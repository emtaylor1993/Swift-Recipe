/**
 * SWIFTRECIPE UNIQUE USERNAME VALIDATION CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the validation class that checks whether a
 *    username is unique.
 * 
 * @packages
 *    Java Extensions Validation (ConstraintValidator, ConstraintValidatorContext)
 *    Spring Framework Beans Factory Annotation (Autowired)
 *    Spring Framework Stereotype (Component)
 *    SwiftRecipe Repository (UserRepository)
 */

package com.swe.swiftrecipe.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.swe.swiftrecipe.repository.UserRepository;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private UserRepository userRepository;

    // Default constructor
    public UniqueUsernameValidator() {
    }

    /**
     * Constructor that uses the injected UserRepository
     * 
     * @param userRepository - The User Repository
     */
    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates the uniqueness of a username
     * 
     * @param username - The username to validate
     * @param context - The validation context
     * @return boolean - True if the username is unique, false otherwise
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null)
            return true;
        return username != null && !userRepository.findByUsername(username).isPresent();
    }
}