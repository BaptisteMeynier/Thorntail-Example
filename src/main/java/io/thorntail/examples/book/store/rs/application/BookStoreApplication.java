package io.thorntail.examples.book.store.rs.application;

import io.thorntail.examples.book.store.rs.resource.BookResource;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class BookStoreApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(BookResource.class);
        return s;
    }
}
