/**
 * SWIFTRECIPE RECIPE NOT FOUND EXCEPTION HANDLER
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents a custom exception to be thrown if a Recipe ID
 *    cannot be found.
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