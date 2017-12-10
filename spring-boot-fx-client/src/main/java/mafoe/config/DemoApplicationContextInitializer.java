package mafoe.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Called when the application context is being constructed.
 */
public class DemoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment springEnvironment = applicationContext.getEnvironment();
        applicationContext.addBeanFactoryPostProcessor(new WireServicesPostProcessor(springEnvironment));
    }
}
