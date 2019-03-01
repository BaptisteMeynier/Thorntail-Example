package io.thorntail.examples.book.store.rs.param.utils;

import io.thorntail.examples.book.store.validation.annotation.MaxPerPage;

public class BookStoreNumberedPageParam extends NumberedPageParam {

    @MaxPerPage
    public Integer getPerPage() {
        return super.getPerPage();
    }

}
