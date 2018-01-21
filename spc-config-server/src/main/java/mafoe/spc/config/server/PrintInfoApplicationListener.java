package mafoe.spc.config.server;

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

        LOG.info("Configurations (.ymls) can be retrieved like this http://localhost:{}/vehicle-data.yml", localServerPort);
        LOG.info("...or this http://localhost:{}/eureka-server.properties", localServerPort);
        LOG.info("...or with profiles and labels, see http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.1.RELEASE/single/spring-cloud-config.html", localServerPort);
    }
}
