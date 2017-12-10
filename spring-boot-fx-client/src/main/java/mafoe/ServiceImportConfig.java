package mafoe;

import mafoe.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

/**
 * Configures the client part of the communication via Spring HTTP Invoker.
 */
@Configuration
public class ServiceImportConfig {

    @Value("${demo.server.url}")
    private String serverUrl;

    @Bean
    public HttpInvokerProxyFactoryBean bookService() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl(serverUrl + "/BookService");
        invoker.setServiceInterface(BookService.class);
        return invoker;
    }
}
