package io.thorntail.examples.book.store.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "AUTHOR")
@NamedQueries({
        @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
})
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lasteName;
    @ManyToOne
    private Book[] books;

    public Author() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasteName() {
        return lasteName;
    }

    public void setLasteName(String lasteName) {
        this.lasteName = lasteName;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(lasteName, author.lasteName) &&
                Arrays.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, firstName, lasteName);
        result = 31 * result + Arrays.hashCode(books);
        return result;
    }
}
