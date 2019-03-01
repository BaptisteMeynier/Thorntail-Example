package io.thorntail.examples.book.store.validation.annotation;

import io.thorntail.examples.book.store.validation.validator.SortedBeanValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SortedBeanValidator.class)
@Target(value = {ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SortedBean {
    String message() default "";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class[] targetBean();
}
