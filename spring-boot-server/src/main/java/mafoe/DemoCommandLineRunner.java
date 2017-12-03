package mafoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * From the spring boot docs:
 * <p>
 * If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner
 * or CommandLineRunner interfaces. Both interfaces work in the same way and offer a single run method which will be
 * called just before SpringApplication.run(…​) completes.
 * </p>
 */
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DemoApplicationListener.class);

    public void run(String... args) {

        LOG.info("DemoCommandLineRunner is called on startup; we could execute some code here");
    }
}