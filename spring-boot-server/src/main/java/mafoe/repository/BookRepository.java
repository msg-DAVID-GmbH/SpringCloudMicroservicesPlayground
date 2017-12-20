package mafoe.repository;

import mafoe.dto.BookDto;
import mafoe.entity.Book;
import mafoe.freemarker.FreemarkerBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.MANDATORY;

/**
 * Spring Data JPA repository for Book entities.
 */
@Transactional(MANDATORY)
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Light weight query to get all books and their authors.
     */
    @Query("SELECT NEW mafoe.freemarker.FreemarkerBook(b.author.name, b.title) " +
            "FROM Book b " +
            "ORDER BY b.author.name")
    List<FreemarkerBook> findBooksForFreemarker();

    /**
     * Light weight query to get all books and their authors.
     */
    @Query("SELECT NEW mafoe.dto.BookDto(b.author.name, b.title) " +
            "FROM Book b " +
            "ORDER BY b.author.name")
    List<BookDto> findBooks();
}
