/**
 * SWIFTRECIPE UNIQUE EMAIL VALIDATION CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the validation class that checks whether an
 *    email is unique.
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
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private UserRepository userRepository;

    // Default constructor
    public UniqueEmailValidator() {
    }

    /**
     * Constructor that uses the injected UserRepository
     * 
     * @param userRepository - The User Repository
     */
    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates the uniqueness of an email
     * 
     * @param email - The email address to validate
     * @param context - The validation context
     * @return boolean - True if the email is unique, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository == null)
            return true;
        return email != null && !userRepository.findByEmail(email).isPresent();
    }
}