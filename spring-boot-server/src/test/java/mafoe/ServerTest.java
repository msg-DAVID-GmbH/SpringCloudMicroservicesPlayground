package mafoe;

import mafoe.dto.BookDto;
import mafoe.entity.Author;
import mafoe.entity.Book;
import mafoe.repository.AuthorRepository;
import mafoe.repository.BookRepository;
import mafoe.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Big clunky test with @{@link SpringBootTest} that loads the entire spring configuration.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testIfServiceWorks() {

        //call service, database is empty so no books should be found
        List<BookDto> books = bookService.loadAllBooks();
        assertNotNull(books);
        assertTrue(books.isEmpty());

        //create test data and save it
        Author duerrenmatt = new Author("Dürrenmatt");
        authorRepository.save(duerrenmatt);
        bookRepository.save(new Book("Der Richter und sein Henker", duerrenmatt));

        //call service again, this time it should return a book
        books = bookService.loadAllBooks();
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals("Dürrenmatt", books.get(0).getAuthor());
        assertEquals("Der Richter und sein Henker", books.get(0).getTitle());
    }
}