package mafoe.config;

import com.google.common.collect.ImmutableSet;
import mafoe.autoremote.client.remoting.ConsumeServiceConfiguration;
import mafoe.service.BookService;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Connects the client with a number of exposed services via HttpInvokerProxyFactoryBean, using Spring HTTP invoker.
 */
@Component
public class ConsumeServicesConfigurationBean implements ConsumeServiceConfiguration, EnvironmentAware {

    private Environment env;

    @Override
    public Set<Class<?>> getServiceInterfaces() {
        return ImmutableSet.of(BookService.class);
    }

    @Override
    public String getServerUrl() {
        return env.getProperty("demo.server.url");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
