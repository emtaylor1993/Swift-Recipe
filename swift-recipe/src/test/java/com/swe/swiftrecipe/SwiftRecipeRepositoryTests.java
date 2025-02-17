/**
 * SWIFTRECIPE REPOSITORY TEST SUITE
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the repository test suite for SwiftRecipe
 * 
 * @packages
 *    JUnit Jupiter API Assertions (assertNotNull)
 *    JavaX Validation (Validator)
 *    JUnit Jupiter API (BeforeEach, Test)
 *    JUnit Jupiter API Extension (ExtendWith)
 *    Mockito JUnit Jupiter (MockitoExtension)
 *    Spring Framework Beans Factory Annotation (Autowired)
 *    Spring Framework Boot Test Autoconfigure ORM JPA (DataJpaTest, TestEntityManager)
 *    Spring Framework Test Context (ContextConfiguration)
 *    Spring Framework Test Context JUnit Jupiter (SpringJUnitConfig)
 *    SwiftRecipe Entitites (Recipe, User)
 */

package com.swe.swiftrecipe;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.swe.swiftrecipe.entity.Recipe;
import com.swe.swiftrecipe.entity.User;

@SpringJUnitConfig
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class SwiftRecipeRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private Validator validator;

    private Recipe recipe;
    private User user;
 
    /**
     * Runs before each test
     */
    @BeforeEach
    public void setup() {
        // Creates a Recipe instance
        recipe = new Recipe();
        recipe.setRecipeName("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setIngredients("Test ingredients");
        recipe.setInstructions("Test instructions".getBytes());
        recipe.setPrepTime(30);
        recipe.setCookTime(60);
        recipe.setServings(4);
        recipe.setDifficulty("Easy");
        recipe.setCuisine("Italian");
        recipe.setCalories(300);
        recipe.setTags("Test tags");
        recipe.setImage("test.jpg");
        recipe.setRating(4.5);
        recipe.setReviewCount(10);
        recipe.setMealType("Lunch");

        // Creates a User instance
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");
    }

    /**
     * Tests whether the application context loads successfully
     */
    @Test
    void contextLoads() {
        assertNotNull(entityManager);
        assertNotNull(validator);
    }

    /**
     * Tests if a Recipe entity can be persisted with valid data
     */
    @Test
    public void recipeEntityShouldBePersistedWithValidData() {
        Recipe savedRecipe = entityManager.persistAndFlush(recipe);
        assertNotNull(savedRecipe.getRecipeId());
    }

    /**
     * Tests if a User entity can be persisted with valid data
     */
    @Test
    public void userEntityShouldBePersistedWithValidData() {
        User savedUser = entityManager.persistAndFlush(user);
        assertNotNull(savedUser.getUserId());
    }
}