/**
 * SWIFTRECIPE RECIPE SERVICE IMPLEMENTATION CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the service implementation for Recipes.
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

@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    RecipeRepository recipeRepository;

    /**
     * Retrieves a recipe by ID
     * 
     * @param id - The ID of the recipe
     * @return Recipe - The recipe associated with the given ID
     * @throws RecipeNotFoundExceptions - Handles if a recipe cannot be found
     */
    @Override
    public Recipe getRecipe(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return unwrapRecipe(recipe, id);
    }

    /**
     * Saves a recipe to the H2 database
     * 
     * @param recipe - The recipe to be saved
     * @return Recipe - The saved recipe
     */
    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe based on the associated ID
     * 
     * @param id - The ID of the recipe to be deleted
     */
    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    /**
     * Retrieves all recipes
     * 
     * @return List<Recipe> - A list of all the recipes
     */
    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    /**
     * Unwrap an Optional Recipe entity
     * 
     * @param entity - The optional recipe entity
     * @param id - The ID of the recipe to be unwrapped
     * @return Recipe - The unwrapped recipe entity
     * @throws RecipeNotFoundException - Handles if a recipe cannot be found
     */
    static Recipe unwrapRecipe(Optional<Recipe> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new RecipeNotFoundException(id);
    }
}