package mafoe.service;

import mafoe.dto.BookDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service for loading books.
 */
@Validated
public interface BookService {

    /**
     * Loads all books, sorted by author and name.
     */
    @Valid
    @NotNull
    List<BookDto> loadAllBooks();
}
