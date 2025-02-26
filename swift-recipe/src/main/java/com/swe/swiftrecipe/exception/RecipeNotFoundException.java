/**
 * SWIFTRECIPE RECIPE NOT FOUND EXCEPTION HANDLER

 * @author Emmanuel Taylor
 * 
 * @description
 *    This class defines a custom exception to be thrown when a requested recipe
 *    ID does not exist in the database. It extends {@link RuntimeException}, making 
 *    it an unchecked exception that can be thrown without requiring explicit handling
 *    in the method signature.
 * 
 * @packages
 *    None
 */

package com.swe.swiftrecipe.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long recipeId) {
        super("The Recipe ID: '" + recipeId + "' does not exist in our records.");
    }
}