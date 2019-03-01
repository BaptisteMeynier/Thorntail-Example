package io.thorntail.examples.book.store.model.mapper;

import io.thorntail.examples.book.store.model.Author;
import io.thorntail.examples.book.store.model.dto.AuthorDto;
import org.mapstruct.Mapper;

@Mapper(uses= BookMapper.class)
public interface AuthorMapper {

    AuthorDto authorToAuthorDto(Author author);
}
