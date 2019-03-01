package io.thorntail.examples.book.store.rs.param.utils;


import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class NumberedPageParam {
    @QueryParam("page")
    @DefaultValue("1")
    @Min(
            value = 1L,
            message = ""
    )
    private Integer page;
    @QueryParam("perPage")
    @Min(
            value = 1L,
            message = ""
    )
    private Integer perPage;

    public NumberedPageParam() {
    }

    public int getPage() {
        return this.page != null && this.page > 0 ? this.page : 1;
    }

    public NumberedPageParam page(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPerPage() {
        return this.perPage != null && this.perPage <= 0 ? null : this.perPage;
    }

    public int getPerPage(int defaultValue, int maxValue) {
        return this.perPage != null && this.perPage > 0 ? Math.min(this.perPage, maxValue) : defaultValue;
    }

    public NumberedPageParam perPage(Integer perPage) {
        this.perPage = perPage;
        return this;
    }
}

