package io.thorntail.examples.book.store.rs.param;

import io.thorntail.examples.book.store.model.enums.SortStrategy;

import java.util.Map;

public class SortParam {

    private Map<String, SortStrategy> sort;

    public SortParam() {
    }

    public Map<String, SortStrategy> getSort() {
        return sort;
    }

    public void setSort(Map<String, SortStrategy> sort) {
        this.sort = sort;
    }
}
