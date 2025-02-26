/**
 * SWIFTRECIPE RECIPE REPOSITORY CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This interface acts as the repository for managing Recipe entities
 *    within MySQL. It extends Spring Data JPA's {@link CrudRepository}, providing
 *    CRUD (Create, Read, Update, Delete) operations without requiring the use of
 *    explicit SQL queries.
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