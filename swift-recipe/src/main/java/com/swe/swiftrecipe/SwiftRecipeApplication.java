/**
 * SWIFTRECIPE SOLUTION APPLICATION ENTRY POINT
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the entry point of SwiftRecipe
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
import com.swe.swiftrecipe.repository.UserRepository;
import com.swe.swiftrecipe.utilities.DatabaseManager;
import com.swe.swiftrecipe.utilities.PasswordHasher;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class SwiftRecipeApplication implements CommandLineRunner {
	UserRepository userRepository;
	PasswordHasher passwordHasher;
	DatabaseManager databaseManager;

	/**
	 * The entry point of t he spring boot application
	 * 
	 * @param args - Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SwiftRecipeApplication.class, args);
	}

	/**
	 * Method call after application context is loaded. Hashes passwords and initializes database
	 * 
	 * @param args - CLI arguments
	 * @throws Exception - Handles errors that may occur during initialization
	 */
	@Override
	public void run(String... args) throws Exception {
		passwordHasher.hashPasswords();
		databaseManager.initializeDataBase();
	}
}
