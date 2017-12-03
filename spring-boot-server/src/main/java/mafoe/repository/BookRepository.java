package mafoe.repository;

import mafoe.entity.Book;
import mafoe.freemarker.FreemarkerBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for Book entities.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Light weight query to get all books and their authors.
     */
    @Query("SELECT NEW mafoe.freemarker.FreemarkerBook(b.author.name, b.title) " +
            "FROM Book b " +
            "ORDER BY b.author.name")
    List<FreemarkerBook> findBooks();
}
