package io.wordblast.gameserver.modules.authentication;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation used to perform the
 * {@link io.wordblast.gameserver.modules.authentication.PasswordMatchValidator
 * PasswordMatchValidator} check on a user data transfer object.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    /**
     * Retrieves the error message of the validator.
     * 
     * @return the retrieved error message.
     */
    String message() default "Passwords don't match.";

    /**
     * Retrieves the groups of the validator.
     * 
     * @return the retrieved groups.
     */
    Class<?>[] groups() default {};

    /**
     * Retrieves the payload of the validator.
     * 
     * @return the retrieved payload.
     */
    Class<? extends Payload>[] payload() default {};
}
