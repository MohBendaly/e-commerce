package e_commerce.produit.service;

import e_commerce.produit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.Order;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getorderById(Long id) {
        return orderRepository.findById(id).orElse(null);

    }


    public Order updateorder(Long id, Order order) {
        return orderRepository.save(order);
    }

    public void deleteorder(Long id) {
    }
}
