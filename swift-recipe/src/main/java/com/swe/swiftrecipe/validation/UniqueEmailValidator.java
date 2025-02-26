/**
 * SWIFTRECIPE UNIQUE EMAIL VALIDATION CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the validation logic for the {@link UniqueEmail} constraint
 *    annotation. It ensures that an email provided during User registration is unique
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
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    /**
     * Repository for accessing User data.
     */
    private UserRepository userRepository;

    /**
     * Default constructor required by Spring.
     */
    public UniqueEmailValidator() {
    }

    /**
     * Constructor for dependency injection of {@link UserRepository}.
     * 
     * @param userRepository - The repository used to check existing emails.
     */
    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks whether the provided email is unique within the database.
     * 
     * @param email - The email address to validate.
     * @param context - The validation context providing additional metadata.
     * @return boolean - Returns true if the email is unique, false otherwise.
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository == null)
            return true;
        return email != null && !userRepository.findByEmail(email).isPresent();
    }
}