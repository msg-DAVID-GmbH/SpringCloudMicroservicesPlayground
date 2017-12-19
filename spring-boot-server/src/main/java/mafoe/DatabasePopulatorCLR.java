package mafoe;

import com.github.javafaker.Faker;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

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

    private static final Logger LOG = LoggerFactory.getLogger(DatabasePopulatorCLR.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DatabasePopulatorCLR(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void run(String... args) {

        LOG.info("DatabasePopulatorCLR is trying to populate the database with some test data");

        //let's fake some data

        Multimap<String, String> authorBookMap = HashMultimap.create();

        Faker faker = new Faker();
        Stream.generate(faker::book).limit(500).forEach(book -> authorBookMap.put(book.author(), book.title()));

        for (Map.Entry<String, Collection<String>> entry : authorBookMap.asMap().entrySet()) {

            String authorName = entry.getKey();
            Collection<String> books = entry.getValue();

            Author author = new Author(authorName);
            authorRepository.save(author);

            for (String title : books) {
                Book book = new Book(title, author);
                bookRepository.save(book);
            }
        }

        LOG.info("Created {} authors and {} books", authorRepository.count(), bookRepository.count());
    }
}