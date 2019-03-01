package io.thorntail.examples.book.store.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "BOOK")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
})
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String title;
    @PositiveOrZero
    private int price;
    @PastOrPresent
    private Date publication;
    @NotEmpty
    @OneToMany(fetch = FetchType.LAZY)
    private Author author;

    public Book() {
    }

    public Book(long id, String title, int price, Date publication, Author author) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.publication = publication;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                price == book.price &&
                Objects.equals(title, book.title) &&
                Objects.equals(publication, book.publication) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, publication, author);
    }
}
