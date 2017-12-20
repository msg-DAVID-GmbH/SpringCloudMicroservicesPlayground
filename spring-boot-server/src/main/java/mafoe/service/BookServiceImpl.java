package mafoe.service;

import mafoe.dto.BookDto;
import mafoe.remoting.ExposedService;
import mafoe.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the BookService for the communication with the JavaFX client.
 */
@ExposedService
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> loadAllBooks() {
        return bookRepository.findBooks();
    }
}
