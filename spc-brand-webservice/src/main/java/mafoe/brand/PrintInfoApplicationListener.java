package mafoe.brand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * From the spring boot docs:
 * <p>
 * In addition to the usual Spring Framework events, such as ContextRefreshedEvent, a SpringApplication sends some
 * additional application events.
 * </p>
 */
@Component
public class PrintInfoApplicationListener {

    private static final Logger LOG = LoggerFactory.getLogger(PrintInfoApplicationListener.class);
    private final Environment env;

    public PrintInfoApplicationListener(Environment env) {
        //don't even need to put @Autowired on this constructor because it's the only one
        this.env = env;
    }

    @EventListener
    public void doStuffWhenServerIsUp(ApplicationReadyEvent applicationReadyEvent) {

        String localServerPort = env.getProperty("local.server.port");

        LOG.info("Access the brand microservice at http://localhost:{}/brands", localServerPort);
    }
}
