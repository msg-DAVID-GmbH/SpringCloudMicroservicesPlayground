package mafoe.web;

import mafoe.entity.Order;
import mafoe.repository.OrderRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Return all orders.
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Order> getBrands() {
        return new ArrayList<>(orderRepository.findAll());
    }
}
