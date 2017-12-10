package mafoe.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for books, containing author and title.
 */
public class BookDto implements Serializable {

    @NotNull
    private String author;
    @NotNull
    private String title;

    public BookDto(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
