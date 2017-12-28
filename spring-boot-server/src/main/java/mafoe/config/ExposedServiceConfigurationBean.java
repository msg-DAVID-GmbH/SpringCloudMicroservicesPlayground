package mafoe.config;

import mafoe.autoremote.server.remoting.ExposedServiceConfiguration;
import mafoe.service.DemoService;
import org.springframework.stereotype.Component;

/**
 * A bean the user needs to implement to get the exposing of services functionality.
 */
@Component
public class ExposedServiceConfigurationBean implements ExposedServiceConfiguration {

    @Override
    public Class<?> getMarkerInterface() {
        return DemoService.class;
    }
}
