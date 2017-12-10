package mafoe.config;

import com.google.common.collect.ImmutableSet;
import mafoe.service.BookService;
import mafoe.service.DemoService;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * Connects the client with a number of exposed services via HttpInvokerProxyFactoryBean, using Spring HTTP invoker.
 */
public class WireServicesPostProcessor extends AbstractWireServicesPostProcessor {

    @Override
    protected Set<Class<? extends DemoService>> getServiceInterfaces() {
        return ImmutableSet.of(BookService.class);
    }

    WireServicesPostProcessor(ConfigurableEnvironment springEnvironment) {
        super(springEnvironment);
    }
}
