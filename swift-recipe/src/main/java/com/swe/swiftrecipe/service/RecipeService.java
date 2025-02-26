/**
 * SWIFTRECIPE RECIPE SERVICE CLASS

 * @author Emmanuel Taylor
 * 
 * @description
 *    This interface defines the layer for managing Recipe entities.
 *    It provides methods for retrieving, saving, deleting, and
 *    listing recipes. The service interface allows for separation of business
 *    logic from persistence logic, enesuring a clean architecture for
 *    easy-to-test components.
 * 
 * @packages
 *    Java Utilities (List)
 *    SwiftRecipe Entity (Recipe)
 */

package com.swe.swiftrecipe.service;

import java.util.List;
import com.swe.swiftrecipe.entity.Recipe;

public interface RecipeService {
    Recipe getRecipe(Long recipeId);
    Recipe saveRecipe(Recipe recipe);
    void deleteRecipe(Long recipeId);
    List<Recipe> getAllRecipes();
}