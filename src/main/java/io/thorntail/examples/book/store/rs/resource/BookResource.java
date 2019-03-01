package io.thorntail.examples.book.store.rs.resource;

import io.thorntail.examples.book.store.model.Book;
import io.thorntail.examples.book.store.model.dto.BookDto;
import io.thorntail.examples.book.store.repository.BookStoreRepository;
import io.thorntail.examples.book.store.rs.param.BookParam;
import io.thorntail.examples.book.store.rs.param.SortParam;
import io.thorntail.examples.book.store.rs.param.utils.BookStoreNumberedPageParam;
import io.thorntail.examples.book.store.rs.utils.PageManager;
import io.thorntail.examples.book.store.service.BookService;
import io.thorntail.examples.book.store.validation.annotation.SortedBean;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("book")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class BookResource {

    @Inject
    private BookService bookService;

    @Inject
    private PageManager pageManager;

    @GET
    public Response get(@BeanParam @Valid BookStoreNumberedPageParam numberedPageParam,
                        @BeanParam BookParam book,
                        @QueryParam("sort") @SortedBean(targetBean = Book.class) SortParam sort
) {
        final int pageNumber = numberedPageParam.getPage();
        final int computePerPage = pageManager.computePerPage(numberedPageParam);
        final List<BookDto> books =
                bookService.find( (pageNumber - 1) * computePerPage, computePerPage, book, sort.getSort());

        final int totalEntityCount = bookService.count(book);

        NumberedPage<BookDto> page = NumberedPage.fromPartialData(
                books,
                totalEntityCount,
                pageNumber,
                computePerPage);

        return Response.ok(new GenericEntity<NumberedPage<BookDto>>(page) {
        }).build();
    }


}
