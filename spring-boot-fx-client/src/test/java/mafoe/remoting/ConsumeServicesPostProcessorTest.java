package mafoe.remoting;

import com.google.common.collect.ImmutableSet;
import mafoe.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests that {@link ConsumeServicesPostProcessor} works and correctly creates HttpInvokerProxyFactoryBean instances to
 * consume Spring HTTP invoker services.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ConsumeServicesPostProcessorTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestService proxy;

    @Qualifier("/NameAlreadyTakenService")
    @Autowired
    private Object nameAlreadyTakenService;

    @Test
    public void serviceConsumed() {
        //"TestService" is a result of the interface's name
        Object bean = applicationContext.getBean("/TestService");
        assertNotNull(bean);
        assertTrue(AopUtils.isAopProxy(bean)
                || AopUtils.isJdkDynamicProxy(bean)
                || AopUtils.isCglibProxy(bean));
        assertTrue(bean instanceof TestService);
        assertEquals(proxy, bean);
    }

    @Test
    public void serviceNotConsumedBecauseNameIsAlreadyTaken() {
        Object bean = applicationContext.getBean("/NameAlreadyTakenService");
        assertNotNull(bean);
        assertTrue(bean instanceof String);
        assertEquals(nameAlreadyTakenService, bean);
    }

    @Import({TestConsumeServicesPostProcessor.class})
    @TestConfiguration
    static class TestConfig {

        /**
         * @return blocks the name "/NameAlreadyTakenService", so the {@link TestConsumeServicesPostProcessor} should
         * not try to replace it.
         */
        @Bean(name = "/NameAlreadyTakenService")
        String nameAlreadyTaken() {
            return "";
        }
    }

    /**
     * Should be picked up and consumed.
     */
    private interface TestService extends DemoService {

    }

    /**
     * Should be ignored because the resulting name for the {@link HttpInvokerServiceExporter} is already taken.
     */
    private interface NameAlreadyTakenService extends DemoService {

    }

    private static class TestConsumeServicesPostProcessor extends AbstractConsumeServicesPostProcessor {

        @Override
        protected Set<Class<? extends DemoService>> getServiceInterfaces() {
            return ImmutableSet.of(TestService.class, NameAlreadyTakenService.class);
        }
    }

}