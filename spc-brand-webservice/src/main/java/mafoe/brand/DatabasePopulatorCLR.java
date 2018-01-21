package mafoe.brand;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import mafoe.brand.model.Brand;
import mafoe.brand.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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

    private final BrandRepository brandRepository;

    public DatabasePopulatorCLR(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public void run(String... args) throws IOException {

        LOG.info("DatabasePopulatorCLR is populating the database with some brands");

        ClassPathResource cpr = new ClassPathResource("brands.txt");
        List<String> brandNames = Files.readLines(cpr.getFile(), Charsets.UTF_8);

        brandNames.forEach(brandName -> brandRepository.add(new Brand(brandName)));

        LOG.info("Created {} brands", brandRepository.count());
    }
}