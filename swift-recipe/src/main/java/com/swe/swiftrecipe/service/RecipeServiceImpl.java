/**
 * SWIFTRECIPE RECIPE SERVICE IMPLEMENTATION CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class provides the implementation of the {@link RecipeService} interface.
 *    It handles business logic related to recipe management, including retrieving,
 *    saving, and deleting recipes in MySQL.
 * 
 * @packages
 *    Java Utilities (List, Optional)
 *    Spring Framework Stereotype (Service)
 *    SwiftRecipe Entity (Recipe)
 *    SwiftRecipe Exception (RecipeNotFoundException)
 *    SwiftRecipe Repository (RecipeRepository)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.swe.swiftrecipe.entity.Recipe;
import com.swe.swiftrecipe.exception.RecipeNotFoundException;
import com.swe.swiftrecipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic.
 */
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    /**
     * Repository for accessing and managing recipe entities in MySQL.
     */
    RecipeRepository recipeRepository;

    /**
     * Retrieves a recipe by its unique ID.
     * 
     * @param id - The unique identifier of the recipe.
     * @return Recipe - The {@link Recipe} object corresponding to the given ID.
     * @throws RecipeNotFoundExceptions - Throws if no recipe is found with the given ID.
     */
    @Override
    public Recipe getRecipe(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return unwrapRecipe(recipe, id);
    }

    /**
     * Saves a new recipe or updates an existing one in MySQL.
     * 
     * @param recipe - The {@link Recipe} object to be saved.
     * @return Recipe - The saved recipe object with generated identifiers.
     */
    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe from MySQL based on its ID.
     * 
     * @param id - The unique identifier of the {@link Recipe} to be deleted.
     */
    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    /**
     * Retrieves all recipes that are stored within MySQL.
     * 
     * @return List<Recipe> - A list of all available recipes.
     */
    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    /**
     * Helper method to unwrap an Optional Recipe entity. If the recipe exists, it 
     * returns the Recipe object. Otherwise, it throws a {@link RecipeNotFoundException}.
     * 
     * @param entity - The Optional containing a Recipe entity.
     * @param id - The unique ID of the recipe.
     * @return Recipe - The unwrapped {@link Recipe} object.
     * @throws RecipeNotFoundException - Throws if no recipe is found using the given ID.
     */
    static Recipe unwrapRecipe(Optional<Recipe> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new RecipeNotFoundException(id);
    }
}