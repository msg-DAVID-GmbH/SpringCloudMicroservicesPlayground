package mafoe.service;

import mafoe.dto.BookDto;
import mafoe.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of the BookService for the communication with the JavaFX client.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public List<BookDto> loadAllBooks() {
        return bookRepository.findBooks();
    }
}
