/**
 * SWIFTRECIPE UNIQUE EMAIL CONSTRAINT CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents an annotation that is used to enforce a unique 
 *    email constraint on User entities. It ensures that no two users can
 *    register with the same email address. The actual validation logic
 *    is implemented in the {@link UniqueEmailValidator} class.
 * 
 * @packages
 *    Java Extensions Validation (Constraint, Payload)
 *    Java Lang Annotation (ElementType, Retention, RetentionPolicy, Target)
 */

package com.swe.swiftrecipe.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    /**
     * Defines the default error message to display when validation fails.
     * This message is displayed if an email is already reigstered in the system.
     */
    String message() default "An account already exists with this email";

    /**
     * Defines groups that can be used to apply different validation constraints.
     * Typically left empty unless grouping constraints are required.
     */
    Class<?>[] groups() default {};

    /**
     * Used to associate additional metadata information for validation. Custom
     * implementations may extend this payload for additional processing.
     */
    Class<? extends Payload>[] payload() default {};
}