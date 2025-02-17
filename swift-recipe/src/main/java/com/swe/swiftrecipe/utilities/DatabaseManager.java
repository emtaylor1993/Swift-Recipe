/**
 * SWIFTRECIPE DATABASE MANAGER CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DatabaseManager {
	
	// Database connection information
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:mem:swe-solution";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "";

	// Data file paths
	private static final String DESCRIPTION_FILE_PATH = "src/main/resources/static/data/descriptions.txt";
	private static final String INGREDIENTS_FILE_PATH = "src/main/resources/static/data/ingredients.txt";
	private static final String INSTRUCTIONS_FILE_PATH = "src/main/resources/static/data/instructions.txt";

	// Number of recipes fetched from API
	private static final Integer NUM_RECIPES = 50;

	/**
	 * Initializes H2 database with information from API and data files
	 */
	public void initializeDataBase() {
		try {
			Class.forName(JDBC_DRIVER);
			try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
				createRecipeTable(connection);
				populateRecipes(connection);
			}
		} catch (ClassNotFoundException | SQLException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DDL to create recipe table in H2 database.
	 * 
	 * @param connection - H2 database connection
	 * @throws SQLException - For SQL exceptions.
	 */
	private void createRecipeTable(Connection connection) throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS recipe ("
			+ "recipe_id INT AUTO_INCREMENT PRIMARY KEY,"
			+ "recipe_name VARCHAR(255),"
			+ "description TEXT,"
			+ "ingredients TEXT,"
			+ "instructions BLOB,"
			+ "prep_time INT,"
			+ "cook_time INT,"
			+ "servings INT,"
			+ "difficulty VARCHAR(50),"
			+ "cuisine VARCHAR(50),"
			+ "calories INT,"
			+ "tags VARCHAR(255),"
			+ "image VARCHAR(255),"
			+ "rating DOUBLE,"
			+ "review_count INT,"
			+ "meal_type VARCHAR(50)"
			+ ")";
				
		try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
			preparedStatement.executeUpdate();
		}
	}

	/**
	 * Fetches data from API and populates the recipe table with relevant information
	 * 
	 * @param connection - H2 database connection
	 * @throws SQLException - For SQL exceptions
	 * @throws IOException - For IO exceptions
	 * @throws InterruptedException - For interrupted exceptions
	 */
	@SuppressWarnings("unchecked")
	private void populateRecipes(Connection connection) throws SQLException, IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader descriptionsReader = new BufferedReader(new FileReader(DESCRIPTION_FILE_PATH));
			BufferedReader ingredientsReader = new BufferedReader(new FileReader(INGREDIENTS_FILE_PATH));
			BufferedReader instructionsReader = new BufferedReader(new FileReader(INSTRUCTIONS_FILE_PATH))) {
			for (int recipeIndex = 1; recipeIndex < NUM_RECIPES + 1; recipeIndex++) {
				HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(String.format("https://dummyjson.com/recipes/%d", recipeIndex)))
					.GET()
					.build();
				HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				Map<String, Object> map = mapper.readValue(response.body(), Map.class);
				insertRecipe(connection, map, descriptionsReader.readLine(), ingredientsReader.readLine(), instructionsReader.readLine());
				reformatLongNames(connection, map);
			}
		}
	}

	/**
	 * Inserts fetched recipe information into the H2 database
	 * 
	 * @param connection - H2 database connection
	 * @param map - Map data structure containing recipe information
	 * @param description - Recipe description
	 * @param ingredients - Recipe ingredients
	 * @param instructions - Recipe instructions
	 * @throws SQLException - For SQL exceptions
	 * @throws IOException - For IO exceptions
	 */
	private void insertRecipe(Connection connection, Map<String, Object> map, String description, String ingredients, String instructions) throws SQLException, IOException {
		StringSerializer stringSerializer = new StringSerializer();
		String sql = "INSERT INTO recipe "
			+ "(recipe_name, description, ingredients, instructions, prep_time, cook_time, servings, difficulty, cuisine, calories, tags, image, rating, review_count, meal_type) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, map.get("name").toString());
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, ingredients);
			preparedStatement.setBytes(4, stringSerializer.serializeString(instructions));
			preparedStatement.setInt(5, (int) map.get("prepTimeMinutes"));
			preparedStatement.setInt(6, (int) map.get("cookTimeMinutes"));
			preparedStatement.setInt(7, (int) map.get("servings"));
			preparedStatement.setString(8, map.get("difficulty").toString());
			preparedStatement.setString(9, map.get("cuisine").toString());
			preparedStatement.setInt(10, (int) map.get("caloriesPerServing"));
			preparedStatement.setString(11, map.get("tags").toString());
			preparedStatement.setString(12, map.get("image").toString());
			preparedStatement.setDouble(13, Double.parseDouble(map.get("rating").toString()));
			preparedStatement.setInt(14, Integer.parseInt(map.get("reviewCount").toString()));
			preparedStatement.setString(15, map.get("mealType").toString());
			preparedStatement.executeUpdate();
		}
	}

	/**
	 * Reformats long recipe names within the H2 database.
	 * 
     * @param connection - H2 database connection
     * @param map - Map containing recipe information
     * @throws SQLException - Handles SQL exceptions
     */
    private void reformatLongNames(Connection connection, Map<String, Object> map) throws SQLException {
        String sql = "";
        if (map.get("name").equals("Pesto Pasta with Cherry Tomatoes")) {
            sql = String.format("UPDATE recipe SET recipe_name = '%s' WHERE recipe_name = "
                + "'Pesto Pasta with Cherry Tomatoes'", "Pesto Pasta with Tomatoes");
        }
        
        if (map.get("name").equals("Japanese Matcha Green Tea Ice Cream")) {
            sql = String.format("UPDATE recipe SET recipe_name = '%s' WHERE recipe_name = "
                + "'Japanese Matcha Green Tea Ice Cream'", "Matcha Green Tea Ice Cream");
        }
        
        if (map.get("name").equals("Saag (Spinach) with Makki di Roti")) {
            sql = String.format("UPDATE recipe SET recipe_name = '%s' WHERE recipe_name = "
                + "'Saag (Spinach) with Makki di Roti'", "Spinach with Makki di Roti");
        }

        if (!sql.equals("")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        }
    }
}
