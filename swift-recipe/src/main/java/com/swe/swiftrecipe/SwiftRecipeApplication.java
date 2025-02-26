/**
 * SWIFTRECIPE SOLUTION APPLICATION ENTRY POINT
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class serves as the main entry point for the SwiftRecipe application.
 *    It initializes the Spring Boot framework and sets up the required components.
 * 
 * @packages
 *    Spring Framework Boot (CommandLineRunner, SpringApplication)
 *    Spring Framework Boot AutoConfigure (SpringBootApplication)
 *    SwiftRecipe Repository (UserRepository)
 *    SwiftRecipe Utilities (DatabaseManager, PasswordHasher)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.swe.swiftrecipe.utilities.DatabaseManager;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class SwiftRecipeApplication implements CommandLineRunner {
	
	/**
	 * Injection of DatabaseManager component.
	 */
	DatabaseManager databaseManager;

	/**
	 * The main method serves as the entry point for the Spring Boot application.
	 * It delegates to {@link SpringApplication#run(Class, String...)} to boostrap the
	 * application.
	 * 
	 * @param args - Command line arguments passed when running the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SwiftRecipeApplication.class, args);
	}

	/**
	 * This method is executed after the application context is fully loaded. It 
	 * initializes the database and ensures the required data is preloaded.
	 * 
	 * @param args - Command line arguments passed when running the application.
	 * @throws Exception - Handles errors that may occur during initialization.
	 */
	@Override
	public void run(String... args) throws Exception {
		databaseManager.initializeDataBase();
	}
}