package mafoe.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * JPA DemoEntity modeling a book.
 */
@Entity
public class Book extends DemoEntity {

    @NotNull
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Author author;

    public Book() {
    }

    public Book(String title, Author author) {
        this.title = title;
        author.addBook(this);
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
