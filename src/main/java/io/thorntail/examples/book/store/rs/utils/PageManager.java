package io.thorntail.examples.book.store.rs.utils;

import io.thorntail.examples.book.store.rs.param.utils.NumberedPageParam;

import javax.annotation.Resource;
import javax.inject.Named;
import java.util.Objects;

@Named
public class PageManager {

    @Resource(lookup = "global/book/X-Default-Per-Page")
    private int xDefaultPerPage;

    @Resource(lookup = "global/book/X-Max-Per-Page")
    private int xMaxPerPage;


    public int computePerPage(NumberedPageParam numberedPageParam) {
        final int perPage = Objects.nonNull(numberedPageParam.getPerPage()) ? numberedPageParam.getPerPage() : xDefaultPerPage;
        return numberedPageParam.getPerPage(perPage, xMaxPerPage);
    }
}
