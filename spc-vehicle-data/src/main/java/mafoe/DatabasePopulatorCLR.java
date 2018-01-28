package mafoe;

import com.github.javafaker.Faker;
import mafoe.entity.Order;
import mafoe.model.BrandView;
import mafoe.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

/**
 * From the spring boot docs:
 * <p>
 * If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner
 * or CommandLineRunner interfaces. Both interfaces work in the same way and offer a single run method which will be
 * called just before SpringApplication.run(…​) completes.
 * </p>
 */
@Profile("populate-db")
@Component
public class DatabasePopulatorCLR implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DatabasePopulatorCLR.class);

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public DatabasePopulatorCLR(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void run(String... args) {

        LOG.info("DatabasePopulatorCLR is trying to populate the database with some test data");

        //call the brand webservice
        ResponseEntity<List<BrandView>> rateResponse =
                restTemplate.exchange("http://brand-webservice/brands",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<BrandView>>() {
                        });
        List<BrandView> brands = rateResponse.getBody();
        LOG.info("Got {} brands from the brand webservice", brands.size());

        Faker faker = new Faker();

        createOrders(brands, faker);

        LOG.info("Created {} orders for {} brands", orderRepository.count(), brands.size());
    }

    private void createOrders(List<BrandView> brands, Faker faker) {

        IntStream.rangeClosed(1, 100).forEach((numberIdontCareAbout) -> brands.forEach(brand -> {

            Order order = new Order(brand.getName(),
                    faker.address().country(),
                    faker.color().name(),
                    faker.number().numberBetween(2, 5));
            orderRepository.save(order);
        }));
    }
}