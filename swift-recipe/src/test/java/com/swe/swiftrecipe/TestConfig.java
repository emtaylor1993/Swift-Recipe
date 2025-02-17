/**
 * SWIFTRECIPE REPOSITORY TEST CONFIGURATION
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents a test configuration class for SwiftRecipe repository tests.
 * 
 * @packages
 *    Mockito (Mockito)
 *    Spring Framework Boot Test Context (TestConfiguration)
 *    Spring Framework Context Annotation (Bean)
 *    Spring Framework Validation Bean Validation (LocalValidatorFactoryBean)
 *    SwiftRecipe Utilities (DatabaseManager, PasswordHasher)
 */

package com.swe.swiftrecipe;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import com.swe.swiftrecipe.utilities.DatabaseManager;
import com.swe.swiftrecipe.utilities.PasswordHasher;

@TestConfiguration
public class TestConfig {

    /**
     * Provides a mocked PasswordHasher bean for testing
     * 
     * @return - A mocked PasswordHasher object
     */
    @Bean
    public PasswordHasher passwordHasher() {
        return Mockito.mock(PasswordHasher.class);
    }

    /**
     * Provides a mocked DatabaseManager bean for testing
     * 
     * @return - A mocked DatabaseManager object
     */
    @Bean
    public DatabaseManager databaseManager() {
        return Mockito.mock(DatabaseManager.class);
    }

    /**
     * Provides a LocalValidatorFactoryBean bean for testing
     * 
     * @return - A LocalValidatorFactoryBean object
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}