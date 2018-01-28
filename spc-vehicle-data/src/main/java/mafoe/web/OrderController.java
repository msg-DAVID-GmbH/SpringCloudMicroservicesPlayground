package mafoe.web;

import mafoe.entity.Order;
import mafoe.repository.OrderRepository;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderController(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Return all orders.
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Order> getOrders() {
        return new ArrayList<>(orderRepository.findAll());
    }

    /**
     * Return only public orders.
     */
    @RequestMapping(path = "public", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Order> getPublicOrders() {

        return orderRepository.findAll()
                .stream()
                .filter(order -> isBrandPublic(order.getBrand()))
                .collect(Collectors.toList());
    }

    private Boolean isBrandPublic(String brandName) {
        //call the brand webservice

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://brand-webservice/brands/public")
                .queryParam("name", brandName);

        HttpEntity<Boolean> response = restTemplate.getForEntity(builder.build().encode().toUri(), Boolean.class);
        return response.getBody();
    }
}
