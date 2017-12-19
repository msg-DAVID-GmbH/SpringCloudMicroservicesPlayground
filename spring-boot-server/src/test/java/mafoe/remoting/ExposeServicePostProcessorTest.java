package mafoe.remoting;

import mafoe.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Tests that {@link ExposeServicePostProcessor} works and picks up the @ExposedService annotation, creating a
 * {@link HttpInvokerServiceExporter} to expose it via Spring HTTP invoker.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ExposeServicePostProcessorTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestService implementation;

    @Qualifier("/NameAlreadyTakenService")
    @Autowired
    private Object nameAlreadyTakenService;

    @Test
    public void serviceExposed() {
        //"TestService" is a result of the interface's name
        Object bean = applicationContext.getBean("/TestService");
        assertNotNull(bean);
        assertTrue(bean instanceof HttpInvokerServiceExporter);
        HttpInvokerServiceExporter exporter = (HttpInvokerServiceExporter) bean;
        assertEquals(implementation, exporter.getService());
        assertEquals(TestService.class, exporter.getServiceInterface());
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void serviceNotExposedBecauseNotInheritingFromMarkerInterface() {
        applicationContext.getBean("/NotExtendingDemoServiceService");
    }

    @Test
    public void serviceNotExposedBecauseNameIsAlreadyTaken() {
        Object bean = applicationContext.getBean("/NameAlreadyTakenService");
        assertNotNull(bean);
        assertTrue(bean instanceof String);
        assertEquals(nameAlreadyTakenService, bean);
    }

    @Import({ExposeServicePostProcessor.class, TestServiceImpl.class, NotExtendingDemoServiceServiceImpl.class,
            NameAlreadyTakenServiceImpl.class})
    @TestConfiguration
    static class TestConfig {

        /**
         * @return blocks the name "/NameAlreadyTakenService", so the {@link ExposeServicePostProcessor} should not try
         * to replace it.
         */
        @Bean(name = "/NameAlreadyTakenService")
        String nameAlreadyTaken() {
            return "";
        }
    }

    /**
     * Should be picked up and exposed.
     */
    @ExposedService
    private static class TestServiceImpl implements TestService {

    }

    private interface TestService extends DemoService {

    }

    /**
     * Should not be picked up because it does not inherit from DemoService.
     */
    @ExposedService
    private static class NotExtendingDemoServiceServiceImpl implements NotExtendingDemoServiceService {

    }

    private interface NotExtendingDemoServiceService {

    }

    /**
     * Should be ignored because the resulting name for the {@link HttpInvokerServiceExporter} is already taken.
     */
    @ExposedService
    private static class NameAlreadyTakenServiceImpl implements NameAlreadyTakenService {

    }

    private interface NameAlreadyTakenService extends DemoService {

    }
}