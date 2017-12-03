package mafoe.repository;

import mafoe.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Book entities.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
