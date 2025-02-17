/**
 * SWIFTRECIPE RECIPE SERVICE CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the service interface to be implemented
 *    by the Recipe service.
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