package mafoe;

import mafoe.entity.Author;
import mafoe.entity.Book;
import mafoe.repository.AuthorRepository;
import mafoe.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * From the spring boot docs:
 * <p>
 * If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner
 * or CommandLineRunner interfaces. Both interfaces work in the same way and offer a single run method which will be
 * called just before SpringApplication.run(…​) completes.
 * </p>
 */
@Profile("populate-db")
@Component
public class DatabasePopulatorCLR implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DemoApplicationListener.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DatabasePopulatorCLR(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void run(String... args) {

        LOG.info("DatabasePopulatorCLR is trying to populate the database with some test data");

        Author steinbeck = new Author("John Steinbeck");
        Author duerrenmatt = new Author("Friedrich Dürrenmatt");
        authorRepository.save(Arrays.asList(steinbeck, duerrenmatt));

        Book eastOfEden = new Book("East of Eden", steinbeck);
        Book ofMiceAndMen = new Book("Of Mice and Men", steinbeck);

        Book richter = new Book("Der Richter und sein Henker", duerrenmatt);
        Book verdacht = new Book("Der Verdacht", duerrenmatt);

        bookRepository.save(Arrays.asList(eastOfEden, ofMiceAndMen, richter, verdacht));

        LOG.info("Created 2 authors and 4 books");
    }
}