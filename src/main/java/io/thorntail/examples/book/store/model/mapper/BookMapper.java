package io.thorntail.examples.book.store.model.mapper;

import io.thorntail.examples.book.store.model.Book;
import io.thorntail.examples.book.store.model.dto.BookDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Mapper(uses= AuthorMapper.class)
public interface BookMapper {

    BookDto bookToBookDto(Book book);

    List<BookDto> bookStreamToBookDtos(Stream<Book> books);

    List<BookDto> bookCollectionToBookDtos(Collection<Book> books);
}
