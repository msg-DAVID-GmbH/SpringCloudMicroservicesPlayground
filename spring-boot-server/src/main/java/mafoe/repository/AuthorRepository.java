package mafoe.repository;

import mafoe.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Author entities.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
