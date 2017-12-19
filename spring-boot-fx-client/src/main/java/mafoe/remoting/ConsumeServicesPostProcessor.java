package mafoe.remoting;

import com.google.common.collect.ImmutableSet;
import mafoe.service.BookService;
import mafoe.service.DemoService;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Connects the client with a number of exposed services via HttpInvokerProxyFactoryBean, using Spring HTTP invoker.
 */
@Component
public class ConsumeServicesPostProcessor extends AbstractConsumeServicesPostProcessor {

    @Override
    protected Set<Class<? extends DemoService>> getServiceInterfaces() {
        return ImmutableSet.of(BookService.class);
    }
}
