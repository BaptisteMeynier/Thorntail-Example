package io.thorntail.examples.book.store.validation.validator;


import io.thorntail.examples.book.store.validation.annotation.MaxPerPage;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MaxPerPageValidator implements ConstraintValidator<MaxPerPage, Integer> {

    @Resource(lookup = "global/book/X-Max-Per-Page")
    private int xMaxPerPage;

    public void initialize(MaxPerPage constraint) {
    }

    public boolean isValid(Integer obj, ConstraintValidatorContext context) {
        return Objects.isNull(obj) || obj <= xMaxPerPage ;
    }
}
