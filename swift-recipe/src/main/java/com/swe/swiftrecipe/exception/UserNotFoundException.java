/**
 * SWIFTRECIPE USER NOT FOUND EXCEPTION HANDLER
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class defines a custom exception to be thrown when a requested User
 *    ID does not exist in the database. It extends {@link RuntimeException}, making 
 *    it an unchecked exception that can be thrown without requiring explicit handling
 *    in the method signature.
 * 
 * @packages
 *    None
 */

package com.swe.swiftrecipe.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("The User ID: '" + userId + "' does not exist in our records.");
    }
}