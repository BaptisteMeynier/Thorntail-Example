package io.thorntail.examples.book.store.validation.annotation;



import io.thorntail.examples.book.store.validation.validator.MaxPerPageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxPerPageValidator.class)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MaxPerPage {

    String message() default "";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
