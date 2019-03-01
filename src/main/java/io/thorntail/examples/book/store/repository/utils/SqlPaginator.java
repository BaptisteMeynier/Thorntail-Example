package io.thorntail.examples.book.store.repository.utils;

public class SqlPaginator {
    private final int beginIndex;
    private final int nbResults;

    public SqlPaginator(int beginIndex, int nbResults) {
        this.beginIndex = beginIndex;
        this.nbResults = nbResults;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getNbResults() {
        return nbResults;
    }

}
