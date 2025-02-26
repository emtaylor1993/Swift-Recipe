/**
 * SWIFTRECIPE DATABASE MANAGER CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class is responsible for managing MySQL operations related to
 *    recipes. It initializes the database creation, loads data from an API,
 *    and processes stored recipes.
 * 
 * @packages
 *    Java IO (BufferedReader, FileReader, IOException)
 *    Java Net (URI)
 *    Java Net HTTP (HttpClient, HttpRequest, HttpResponse)
 *    Java SQL (Connection, DriverManager, PreparedStatement, SQLException)
 *    Java Utilities (Map)
 *    Spring Framework Stereotype (Service)
 *    FasterXML Jackcon Databind (ObjectMapper)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic.
 */
@AllArgsConstructor
@Service
public class DatabaseManager {
	
	/**
	 * Injection of the JDBC Template for executing SQL queries.
	 */
	@Autowired
	private final JdbcTemplate jdbcTemplate;

	/**
	 * Injection of the String Serializer for handling instruction storage.
	 */
	@Autowired
	private final StringSerializer stringSerializer;

	/**
	 * File paths for storing descriptions, ingredients, and instructions.
	 */
	private static final String DESCRIPTION_FILE_PATH = "static/data/descriptions.txt";
	private static final String INGREDIENTS_FILE_PATH = "static/data/ingredients.txt";
	private static final String INSTRUCTIONS_FILE_PATH = "static/data/instructions.txt";

	/**
	 * Maximum number of recipes to be fetched from the API.
	 */
	private static final Integer NUM_RECIPES = 50;

	/**
	 * Initializes MySQL by creating the necessary tablse and populating
	 * them with recipe data. Uses {@link Transactional} to ensure data integrity.
	 */
	@Transactional
	public void initializeDataBase() {
		createRecipeTable();
		populateRecipes();
	}

	/**
	 * Creates the recipe table in MySQL if it does not already exist. Uses
	 * the following DDL Schema.
	 */
	private void createRecipeTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS recipe (
                recipe_id INT AUTO_INCREMENT PRIMARY KEY,
                recipe_name VARCHAR(255) NOT NULL,
                description TEXT,
                ingredients TEXT,
                instructions MEDIUMBLOB,
                prep_time INT,
                cook_time INT,
                servings INT,
                difficulty VARCHAR(50),
                cuisine VARCHAR(50),
                calories INT,
                tags VARCHAR(255),
                image VARCHAR(255),
                rating DOUBLE,
                review_count INT,
                meal_type VARCHAR(50)
            );
        """;
        jdbcTemplate.execute(createTableSQL);
	}

	/**
	 * Fetches recipe data from an external API and populates the database. This method
	 * takes into account multiple ways to run the application. Filepaths should be 
	 * handled both running locally or as a JAR file.
	 * 
	 * @throws IOException - Throws if an I/O error occurs during MySQL population.
	 * @throws InterruptedException - Throws if an Interruption occurs during MySQL population.
	 */
    @SuppressWarnings("CallToPrintStackTrace")
	private void populateRecipes() {
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedReader descriptionsReader = getFileReader(DESCRIPTION_FILE_PATH);
             BufferedReader ingredientsReader = getFileReader(INGREDIENTS_FILE_PATH);
             BufferedReader instructionsReader = getFileReader(INSTRUCTIONS_FILE_PATH)) {

            for (int recipeIndex = 1; recipeIndex <= NUM_RECIPES; recipeIndex++) {
                HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(String.format("https://dummyjson.com/recipes/%d", recipeIndex)))
					.GET()
					.build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                @SuppressWarnings("unchecked")
				Map<String, Object> map = mapper.readValue(response.body(), Map.class);
                insertRecipe(map, descriptionsReader.readLine(), ingredientsReader.readLine(), instructionsReader.readLine());
				reformatLongNames(map);
			}
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Inserts data for each fetched recipe into MySQL. Does not insert if the recipe
	 * already exists inside of MySQL to prevent duplicates.
	 * 
	 * @param map - Map data structure containing recipe information.
	 * @param description - String containing textual description of the recipe.
	 * @param ingredients - String containing textual ingredients of the recipe.
	 * @param instructions - String containing textual instructions of the recipe.
	 * @throws IOException - Throws if an I/O error ocurs during insertion.
	 * @throws NumberFormatException - Throws of a Number Format error occurs during insertion.
	 * @throws DataAccessException - Throws if a Data Accesss error occurs during insertion.
	 */
    @SuppressWarnings("CallToPrintStackTrace")
	private void insertRecipe(Map<String, Object> map, String description, String ingredients, String instructions) {
		Map<String, String> nameUpdates = Map.of(
			"Pesto Pasta with Cherry Tomatoes", "Pesto Pasta with Tomatoes",
			"Japanese Matcha Green Tea Ice Cream", "Matcha Green Tea Ice Cream",
			"Saag (Spinach) with Makki di Roti", "Spinach with Makki di Roti"
		);
	
		String recipeName = map.get("name").toString();
		String finalName = nameUpdates.getOrDefault(recipeName, recipeName); // Use updated name if it exists
	
		// Check if the recipe already exists.
		String checkRecipeExists = "SELECT recipe_id FROM recipe WHERE recipe_name = ?";
		Integer existingId;

		try {
			existingId = jdbcTemplate.queryForObject(checkRecipeExists, Integer.class, finalName);
		} catch (EmptyResultDataAccessException e) {
			existingId = null;
		}
	
		if (existingId == null) { // Insert only if it doesn't exist.
			String sql = """
				INSERT INTO recipe (recipe_name, description, ingredients, instructions, prep_time, cook_time, servings, difficulty, cuisine, calories, tags, image, rating, review_count, meal_type)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";
	
			try {
				byte[] serializedInstructions = stringSerializer.serializeString(instructions);
				jdbcTemplate.update(sql,
					finalName, 
					description,
					ingredients,
					serializedInstructions,
					(int) map.get("prepTimeMinutes"),
					(int) map.get("cookTimeMinutes"),
					(int) map.get("servings"),
					map.get("difficulty").toString(),
					map.get("cuisine").toString(),
					(int) map.get("caloriesPerServing"),
					map.get("tags").toString(),
					map.get("image").toString(),
					Double.parseDouble(map.get("rating").toString()),
					Integer.parseInt(map.get("reviewCount").toString()),
					map.get("mealType").toString()
				);
			} catch (IOException | NumberFormatException | DataAccessException e) {
				e.printStackTrace();
			}
		}
    }

	/**
	 * Reformats longer recipe names in MySQL for better readability.
	 * 
     * @param map - Map containing recipe information.
     */
    private void reformatLongNames(Map<String, Object> map) {
		Map<String, String> nameUpdates = Map.of(
			"Pesto Pasta with Cherry Tomatoes", "Pesto Pasta with Tomatoes",
			"Japanese Matcha Green Tea Ice Cream", "Matcha Green Tea Ice Cream",
			"Saag (Spinach) with Makki di Roti", "Spinach with Makki di Roti"
		);
	
		if (nameUpdates.containsKey(map.get("name"))) {
			String oldName = map.get("name").toString();
			String newName = nameUpdates.get(oldName);
	
			// Check if the update is actually needed
			String checkSQL = "SELECT COUNT(*) FROM recipe WHERE recipe_name = ?";
			int count = jdbcTemplate.queryForObject(checkSQL, Integer.class, oldName);
	
			if (count > 0) { // Only update if the old name exists
				String updateSQL = "UPDATE recipe SET recipe_name = ? WHERE recipe_name = ?";
				jdbcTemplate.update(updateSQL, newName, oldName);
			}
		}
    }

	/**
	 * Retrieves a BufferedReader for the given file path.
	 * 
	 * @param filePath - The path of the file.
	 * @return BufferedReader for reading the file.
	 * @throws IOException - Thrown if an I/O error occurs during the operation.
	 */
    private static BufferedReader getFileReader(String filePath) throws IOException {
        InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found in classpath: " + filePath);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
