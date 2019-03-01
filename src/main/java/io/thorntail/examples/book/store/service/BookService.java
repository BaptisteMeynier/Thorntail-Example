package io.thorntail.examples.book.store.service;

import io.thorntail.examples.book.store.rs.param.BookParam;
import io.thorntail.examples.book.store.model.dto.BookDto;
import io.thorntail.examples.book.store.model.enums.SortStrategy;
import io.thorntail.examples.book.store.repository.BookStoreRepository;
import io.thorntail.examples.book.store.repository.utils.SqlPaginator;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BookService {

    @Inject
    private BookStoreRepository bookStoreRepository;

    public List<BookDto> find(final int beginIndex, final int nbResult, final BookParam bookParam, Map<String, SortStrategy> sort) {
        final SqlPaginator sqlPaginator = new SqlPaginator(beginIndex,nbResult);

        return bookStoreRepository.findBook(sqlPaginator,bookParam,sort);
    }

    public int count(final  BookParam bookParam) {
        return bookStoreRepository.countBook(bookParam);
    }
}
