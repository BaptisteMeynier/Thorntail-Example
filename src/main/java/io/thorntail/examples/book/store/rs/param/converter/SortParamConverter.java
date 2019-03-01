package io.thorntail.examples.book.store.rs.param.converter;



import io.thorntail.examples.book.store.model.enums.SortStrategy;
import io.thorntail.examples.book.store.rs.param.SortParam;

import javax.ws.rs.ext.ParamConverter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class SortParamConverter implements ParamConverter<SortParam> {
    @Override
    public SortParam fromString(String sort) {
        Map<String, SortStrategy> res = new HashMap<>();
        final String regex = "(\\w+):(asc|desc)";
        final java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        final Function<Matcher, String> sortField = aMatcher -> aMatcher.group(1);
        final Function<Matcher, SortStrategy> sortStrategy = aMatcher -> SortStrategy.valueOf(aMatcher.group(2));
        if (Objects.nonNull(sort)) {
            res = Arrays.stream(sort.split(","))
                    .map(p::matcher)
                    .filter(Matcher::matches)
                    .filter(matcher -> matcher.groupCount() == 2)
                    .collect(Collectors.toMap(sortField, sortStrategy));
        }
        SortParam sp = new SortParam();
        sp.setSort(res);
        return sp;
    }

    @Override
    public String toString(SortParam stringSortStrategyMap) {
        return stringSortStrategyMap.toString();
    }

}
