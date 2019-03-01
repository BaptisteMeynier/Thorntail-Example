package io.thorntail.examples.book.store.model.dto;

import java.util.Date;

public class BookDto {
    public long id;
    public String title;
    public int price;
    public Date publication;
    public AuthorDto author;
}
