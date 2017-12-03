package mafoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDemoListener {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDemoListener.class);

    @EventListener
    public void doStuffWhenServerIsUp(ApplicationReadyEvent applicationReadyEvent) {

        LOG.info("ApplicationDemoListener triggered with {}", applicationReadyEvent);
        LOG.info("Now the server is up and we could fill caches and other stuff");
    }
}
