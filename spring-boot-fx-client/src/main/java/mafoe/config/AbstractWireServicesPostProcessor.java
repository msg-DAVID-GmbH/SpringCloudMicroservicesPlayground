package mafoe.config;

import mafoe.service.DemoService;
import mafoe.util.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Connects the client with a number of exposed services via HttpInvokerProxyFactoryBean, using Spring HTTP invoker.
 */
public abstract class AbstractWireServicesPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractWireServicesPostProcessor.class);

    protected abstract Set<Class<? extends DemoService>> getServiceInterfaces();

    private ConfigurableEnvironment springEnvironment;

    protected AbstractWireServicesPostProcessor(ConfigurableEnvironment springEnvironment) {
        this.springEnvironment = springEnvironment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        String serverUrl = springEnvironment.getProperty("demo.server.url");
        getServiceInterfaces().forEach(serviceInterface -> wireService(serviceInterface, registry, serverUrl));
    }

    private void wireService(Class<? extends DemoService> serviceInterface, BeanDefinitionRegistry registry, String serverUrl) {

        String endpointUrl = serverUrl + RemotingHelper.serviceInterfaceToEndpoint(serviceInterface);

        AbstractBeanDefinition proxyDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(HttpInvokerProxyFactoryBean.class.getName())
                .addPropertyValue("serviceUrl", endpointUrl)
                .addPropertyValue("serviceInterface", serviceInterface)
                .getBeanDefinition();

        String proxyBeanName = StringUtils.uncapitalize(serviceInterface.getSimpleName());
        if (registry.containsBeanDefinition(proxyBeanName)) {
            LOG.warn("While trying to register a proxy with the name {} for the endpoint {}, "
                            + " a bean with that name was already defined: {}",
                    proxyBeanName,
                    endpointUrl,
                    registry.getBeanDefinition(proxyBeanName));
        } else {
            LOG.info("Wiring proxy {} to the endpoint {}", proxyBeanName, endpointUrl);
            registry.registerBeanDefinition(proxyBeanName, proxyDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}