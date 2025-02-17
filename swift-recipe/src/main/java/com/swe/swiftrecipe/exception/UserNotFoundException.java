/**
 * SWIFTRECIPE USER NOT FOUND EXCEPTION HANDLER
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents a custom exception to be thrown if a User ID
 *    cannot be found.
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