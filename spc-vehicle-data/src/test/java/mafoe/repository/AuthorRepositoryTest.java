package mafoe.repository;

import mafoe.entity.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sliced Spring Data JPA test. Instead of loading the entire Spring configuration, {@link DataJpaTest} only loads
 * what we need for testing the persistence layer.
 * <br/>
 * {@link DataJpaTest} pulls in the {@link org.springframework.transaction.annotation.Transactional} annotation - in
 * case somebody wonders how this test works without obvious transactional support, seeing that {@link AuthorRepository}
 * is annotated with @Transactional(MANDATORY).
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository repository;

    @Test
    public void testFindNothing() {
        assertThat(repository.findByName("Walter Moers")).isNull();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testFindByName() {
        em.persist(new Author("Walter Moers"));
        Author author = repository.findByName("Walter Moers");
        assertThat(author.getName()).isEqualTo("Walter Moers");
    }

    @Test
    public void testFindOne() {
        Author walter_moers = em.persist(new Author("Walter Moers"));
        Author author = repository.findOne(walter_moers.getId());
        assertThat(author.getName()).isEqualTo("Walter Moers");
        assertThat(author).isEqualTo(walter_moers);
        assertThat(author.getId()).isEqualTo(walter_moers.getId());
    }

    @Test
    public void testFindAll() {
        em.persist(new Author("Walter Moers"));
        em.persist(new Author("Hildegunst von Mythenmetz"));
        List<Author> all = repository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }
}