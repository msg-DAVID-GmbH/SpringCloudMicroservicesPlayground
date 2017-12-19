package mafoe.remoting;

import mafoe.service.DemoService;
import mafoe.util.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import java.util.Set;

/**
 * Connects the client with a number of exposed services via HttpInvokerProxyFactoryBean, using Spring HTTP invoker.
 */
public abstract class AbstractConsumeServicesPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeServicesPostProcessor.class);
    private Environment environment;

    protected abstract Set<Class<? extends DemoService>> getServiceInterfaces();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        String serverUrl = environment.getProperty("demo.server.url");
        getServiceInterfaces().forEach(serviceInterface -> consumeService(serviceInterface, registry, serverUrl));
    }

    private void consumeService(Class<? extends DemoService> serviceInterface, BeanDefinitionRegistry registry, String serverUrl) {

        String endpointUrl = serverUrl + RemotingHelper.serviceInterfaceToEndpoint(serviceInterface);

        AbstractBeanDefinition proxyDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(HttpInvokerProxyFactoryBean.class.getName())
                .addPropertyValue("serviceUrl", endpointUrl)
                .addPropertyValue("serviceInterface", serviceInterface)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getBeanDefinition();

        String proxyBeanName = RemotingHelper.serviceInterfaceToEndpoint(serviceInterface);
        if (registry.containsBeanDefinition(proxyBeanName)) {
            LOG.warn("While trying to register a proxy with the name {} for the endpoint {}, "
                            + " a bean with that name was already defined: {}",
                    proxyBeanName,
                    endpointUrl,
                    registry.getBeanDefinition(proxyBeanName));
        } else {
            LOG.info("Consuming a service at the endpoint {} via the proxy bean {}", endpointUrl, proxyBeanName);
            registry.registerBeanDefinition(proxyBeanName, proxyDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}