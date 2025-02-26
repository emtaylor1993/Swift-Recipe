/**
 * SWIFTRECIPE UNIQUE USERNAME VALIDATION CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the validation logic for the {@link UniqueUsername} constraint
 *    annotation. It ensures that a username provided during User registration is unique
 *    within the system.
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

    /**
     * Repository for accessing User data.
     */
    private UserRepository userRepository;

    /**
     * Default constructor required by Spring.
     */    public UniqueUsernameValidator() {
    }

    /**
     * Constructor for dependency injection of {@link UserRepository}.
     * 
     * @param userRepository - The repository used to check existing emails.
     */
    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
    /**
     * Checks whether the provided username is unique within the database.
     * 
     * @param email - The username to validate.
     * @param context - The validation context providing additional metadata.
     * @return boolean - Returns true if the username is unique, false otherwise.
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null)
            return true;
        return username != null && !userRepository.findByUsername(username).isPresent();
    }
}