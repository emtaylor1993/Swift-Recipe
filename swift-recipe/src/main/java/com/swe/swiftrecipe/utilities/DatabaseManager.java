/**
 * SWIFTRECIPE DATABASE MANAGER CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class contains functionality to support the initial load of
 *    data into the H2 console as well as results from the Dummy JSON API.
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
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DatabaseManager {
	
	@Autowired
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	private final StringSerializer stringSerializer;

	// Data file paths
	private static final String DESCRIPTION_FILE_PATH = System.getenv("DESCRIPTION_FILE_PATH") != null
		? System.getenv("DESCRIPTION_FILE_PATH") : "src/main/resources/static/data/descriptions.txt";
	private static final String INGREDIENTS_FILE_PATH = System.getenv("INGREDIENTS_FILE_PATH") != null
		? System.getenv("DESCRIPTION_FILE_PATH") : "src/main/resources/static/data/ingredients.txt";
	private static final String INSTRUCTIONS_FILE_PATH = System.getenv("INSTRUCTIONS_FILE_PATH") != null
		? System.getenv("DESCRIPTION_FILE_PATH") : "src/main/resources/static/data/instructions.txt";

	// ATTENTION: If running locally using mvn package, comment the above out and uncomment these lines.
	
	// private static final String DESCRIPTION_FILE_PATH = System.getenv("DESCRIPTION_FILE_PATH") != null
	// 	? System.getenv("DESCRIPTION_FILE_PATH") : "classes/static/data/descriptions.txt";
	// private static final String INGREDIENTS_FILE_PATH = System.getenv("INGREDIENTS_FILE_PATH") != null
	// 	? System.getenv("DESCRIPTION_FILE_PATH") : "classes/static/data/ingredients.txt";
	// private static final String INSTRUCTIONS_FILE_PATH = System.getenv("INSTRUCTIONS_FILE_PATH") != null
	// 	? System.getenv("DESCRIPTION_FILE_PATH") : "classes/static/data/instructions.txt";

	// Number of recipes fetched from API
	private static final Integer NUM_RECIPES = 50;

	/**
	 * Initializes H2 database with information from API and data files
	 */
	@Transactional
	public void initializeDataBase() {
		createRecipeTable();
		populateRecipes();
	}

	/**
	 * DDL to create recipe table in H2 database.
	 * 
	 * @throws SQLException - For SQL exceptions.
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
	 * Fetches data from API and populates the recipe table with relevant information
	 * 
	 * @throws SQLException - For SQL exceptions
	 * @throws IOException - For IO exceptions
	 * @throws InterruptedException - For interrupted exceptions
	 */
    @SuppressWarnings("CallToPrintStackTrace")
	private void populateRecipes() {
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedReader descriptionsReader = new BufferedReader(new FileReader(DESCRIPTION_FILE_PATH));
             BufferedReader ingredientsReader = new BufferedReader(new FileReader(INGREDIENTS_FILE_PATH));
             BufferedReader instructionsReader = new BufferedReader(new FileReader(INSTRUCTIONS_FILE_PATH))) {

            for (int recipeIndex = 1; recipeIndex <= NUM_RECIPES; recipeIndex++) {
                HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(String.format("https://dummyjson.com/recipes/%d", recipeIndex)))
					.GET()
					.build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                Map<String, Object> map = mapper.readValue(response.body(), Map.class);
                insertRecipe(map, descriptionsReader.readLine(), ingredientsReader.readLine(), instructionsReader.readLine());
				reformatLongNames(map);
			}
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Inserts fetched recipe information into the H2 database
	 * 
	 * @param map - Map data structure containing recipe information
	 * @param description - Recipe description
	 * @param ingredients - Recipe ingredients
	 * @param instructions - Recipe instructions
	 * @throws SQLException - For SQL exceptions
	 * @throws IOException - For IO exceptions
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
	
		// Check if the recipe already exists
		String checkRecipeExists = "SELECT recipe_id FROM recipe WHERE recipe_name = ?";
		Integer existingId;

		try {
			existingId = jdbcTemplate.queryForObject(checkRecipeExists, Integer.class, finalName);
		} catch (EmptyResultDataAccessException e) {
			existingId = null;
		}
	
		if (existingId == null) { // Insert only if it doesn't exist
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
	 * Reformats long recipe names within the H2 database.
	 * 
     * @param map - Map containing recipe information
     * @throws SQLException - Handles SQL exceptions
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
}
