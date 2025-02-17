/**
 * SWIFTRECIPE UNIQUE EMAIL CONSTRAINT CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the interface to be implemented by the 
 *    custom UniqueEmailValidator class.
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
     * Error message to print when validation fails.
     * 
     * @return String - The error message
     */
    String message() default "An account already exists with this email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}