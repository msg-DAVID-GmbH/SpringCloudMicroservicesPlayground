package mafoe.remoting;

import javafx.util.Pair;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * During start of the spring container, checks for beans annotated with @ExposedService, and if that bean implements
 * an interface derived from DemoService.class, an HttpInvokerServiceExporter bean is dynamically contructed that
 * exposes the bean above via Spring HTTP invoker. The endpoint's name starts with a slash followed by the simple name
 * of the interface derived from DemoService.class.
 * <br/>
 * Example: There is a service implementation BookServiceImpl.java that is annotated with @ExposedService. That bean
 * is found by this postprocessor. This postprocessor finds that BookServiceImpl.java implements BookService.java,
 * which extends DemoService.java. Then a dynamic HttpInvokerServiceExporter bean instance is created at the endpoint
 * "/BookService" to expose the service implementation for the Spring HTTP invoker mechanism.
 */
@Component
public class ExposeServicePostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(ExposeServicePostProcessor.class);

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        checkForExposedServiceConfiguration();

        //iterate over all known beans
        Arrays.stream(registry.getBeanDefinitionNames())
                .map(beanName -> new Pair<>(beanName, registry.getBeanDefinition(beanName)))
                // find all beans annotated with @ExposedService
                .filter(this::filterExposedServices)
                // create a HttpInvokerServiceExporter bean for all suitable beans
                .forEach(pair -> exposeService(pair, registry));
    }

    private boolean filterExposedServices(Pair<String, BeanDefinition> pair) {

        BeanDefinition beanDefinition = pair.getValue();

        if (beanDefinition.getBeanClassName() == null) {
            //apparently, these bean definitions exist, and we need to ignore them
            return false;
        }

        try {
            Class<?> serviceImplementationClass = Class.forName(beanDefinition.getBeanClassName());
            ExposedService exposedService =
                    AnnotationUtils.findAnnotation(serviceImplementationClass, ExposedService.class);
            return exposedService != null;
        } catch (ClassNotFoundException e) {
            LOG.warn("Error when scanning for services to expose: {}", e);
        }
        return false;
    }

    private void exposeService(Pair<String, BeanDefinition> pair, BeanDefinitionRegistry registry) {

        String serviceImplementationBeanName = pair.getKey();
        BeanDefinition serviceImplementationBeanDefinition = pair.getValue();
        ExposedServiceConfiguration configuration = applicationContext.getBean(ExposedServiceConfiguration.class);
        Class<?> serviceInterfaceClass = findMarkerInterface(serviceImplementationBeanDefinition, configuration);

        if (serviceInterfaceClass == null) {
            LOG.warn("Service {} was annotated with @ExposedService, but does not implement an interface derived from" +
                            " the required marker interface {}",
                    serviceImplementationBeanName, configuration.getMarkerInterface().getName());
            return;
        }

        AbstractBeanDefinition httpExporterDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(HttpInvokerServiceExporter.class.getName())
                .addPropertyReference("service", serviceImplementationBeanName)
                .addPropertyValue("serviceInterface", serviceInterfaceClass)
                .addPropertyValue("remoteInvocationExecutor", new CustomRemoteInvocationExecutor())
                .getBeanDefinition();

        String httpExporterBeanName = RemotingHelper.serviceInterfaceToEndpoint(serviceInterfaceClass);
        if (registry.containsBeanDefinition(httpExporterBeanName)) {
            LOG.warn("While trying to expose the service bean '{}' via an HttpInvokerServiceExporter with the name {},"
                            + " a bean with that name was already defined: {}", serviceImplementationBeanName,
                    httpExporterBeanName, registry.getBeanDefinition(httpExporterBeanName));
        } else {
            LOG.info("Exposing service bean {} at the endpoint {}", serviceImplementationBeanName,
                    httpExporterBeanName);
            registry.registerBeanDefinition(httpExporterBeanName, httpExporterDefinition);
        }
    }

    private Class<?> findMarkerInterface(BeanDefinition serviceImplementationBeanDefinition,
                                         ExposedServiceConfiguration configuration) {

        try {
            Class<?> serviceImplementationClass = Class.forName(serviceImplementationBeanDefinition.getBeanClassName());
            Set<Class<?>> interfaces = ClassUtils.getAllInterfacesForClassAsSet(serviceImplementationClass);
            for (Class<?> anInterface : interfaces) {
                if (configuration.getMarkerInterface().isAssignableFrom(anInterface)
                        && !anInterface.equals(configuration.getMarkerInterface())) {
                    return anInterface;
                }
            }
            return null;

        } catch (ClassNotFoundException e) {
            LOG.warn("Error when scanning for services to expose: {}", e);
            return null;
        }
    }

    private void checkForExposedServiceConfiguration() {

        ExposedServiceConfiguration configuration = applicationContext.getBean(ExposedServiceConfiguration.class);

        if (configuration.getMarkerInterface() == null) {
            throw new RuntimeException("The bean implementing ExposedServiceConfiguration did not return a marker " +
                    "interface class");
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}