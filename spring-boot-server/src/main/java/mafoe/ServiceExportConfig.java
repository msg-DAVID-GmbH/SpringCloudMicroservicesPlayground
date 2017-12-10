package mafoe;

import mafoe.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

/**
 * Configuration for HTTP Invoker communication with the JavaFX client.
 */
@Configuration
public class ServiceExportConfig {

    private final BookService bookService;

    public ServiceExportConfig(BookService bookService) {
        this.bookService = bookService;
    }

    @Bean(name = "/BookService")
    HttpInvokerServiceExporter bookService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(bookService);
        exporter.setServiceInterface(BookService.class);
        return exporter;
    }
}
