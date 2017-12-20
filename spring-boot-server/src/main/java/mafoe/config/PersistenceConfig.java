package mafoe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Override the Spring Autoconfigure definition of Spring Data JPA.
 * <br/>
 * Now we need to specify the name of the package that contains the repositories, but we get to set
 * enableDefaultTransactions to false. Sadly it doesn't work: the repositories still create a transaction if none is
 * present. This can be tested by removing the annotations on the repositories and in
 * {@link mafoe.service.BookServiceImpl}.
 */
@EnableJpaRepositories(
        enableDefaultTransactions = false,
        basePackages = {"mafoe.repository"})
@Configuration
public class PersistenceConfig {
}
