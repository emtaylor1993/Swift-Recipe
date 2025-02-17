/**
 * SWIFTRECIPE RECIPE REPOSITORY CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the repository to hold Recipe objects
 *    that are loaded into the database.
 * 
 * @packages
 *    Spring Framework Data Repository (CrudRepository)
 *    SwiftRecipe Entity (Recipe)
 */

package com.swe.swiftrecipe.repository;

import org.springframework.data.repository.CrudRepository;
import com.swe.swiftrecipe.entity.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    
}