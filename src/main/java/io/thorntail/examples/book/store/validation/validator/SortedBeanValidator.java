package io.thorntail.examples.book.store.validation.validator;



import io.thorntail.examples.book.store.model.annotation.SortedField;
import io.thorntail.examples.book.store.rs.param.SortParam;
import io.thorntail.examples.book.store.validation.annotation.SortedBean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortedBeanValidator implements ConstraintValidator<SortedBean, SortParam> {

    private static final String GLOBAL_REGEX = "((\\w*:(asc|desc)),?)+";
    private static final String CAPTURE_REGEX = "(\\w+):(asc|desc)";
    private static final Pattern GLOBAL_COMPILE = Pattern.compile(GLOBAL_REGEX);
    private static final Pattern CAPTURE_COMPILE = Pattern.compile(CAPTURE_REGEX);

    private Class[] targetedBean;

    @Override
    public void initialize(SortedBean sortFilter) {
        targetedBean = sortFilter.targetBean();
    }

    @Override
    public boolean isValid(SortParam sortParam, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = Objects.isNull(sortParam.getSort()) || sortParam.getSort().isEmpty();

        if (!valid && Objects.nonNull(targetedBean) /*&& checkGlobal(sortParam)*/) {
            valid = checkFields(sortParam.getSort().keySet());
        }
        return valid;
    }

    /*private boolean checkGlobal(SortParam sortParam) {
        String sort = sortParam.getSort().keySet().stream().findFirst().get();
        return !Objects.nonNull(sortParam.getSort()) || GLOBAL_COMPILE.matcher(sort).matches();
    }*/


    private boolean checkFields(Set<String> sort) {
        final Predicate<Matcher> isExistingField = aMatcher ->
                Arrays.stream(targetedBean)
                        .map(Class::getDeclaredFields)
                        .flatMap(Arrays::stream)
                        .filter(field -> Objects.nonNull(field.getAnnotation(SortedField.class)))
                        .anyMatch(field -> field.getName().equals(aMatcher.group(1)));

        return sort.stream()
                .map(CAPTURE_COMPILE::matcher)
                .allMatch(isExistingField);
    }
}
