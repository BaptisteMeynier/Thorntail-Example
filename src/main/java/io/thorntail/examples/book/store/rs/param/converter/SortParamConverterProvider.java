package io.thorntail.examples.book.store.rs.param.converter;

import io.thorntail.examples.book.store.rs.param.SortParam;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

//@Provider

public class SortParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if(aClass.getName().equals(SortParam.class.getName())){
            return (ParamConverter<T>) new SortParamConverter();
        }
        return null;
    }
}
