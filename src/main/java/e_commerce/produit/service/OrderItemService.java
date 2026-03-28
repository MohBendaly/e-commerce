package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.OrderItem;
import e_commerce.produit.repository.OrderItemRepository;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderRepository.findAll();
    }

    public OrderItem saveOrderItem(OrderItem orderitem) {
        return orderRepository.save(orderitem);
    }

    public List<OrderItem> saveorderitem(OrderItem orderitem) { return orderRepository.saveAll(List.of(orderitem));
    }

    public List<OrderItem> updateorderitem(Long id, OrderItem orderitem) { return orderRepository.saveAll(List.of(orderitem));
    }

    public void deleteorderitem(Long id) {
    }
}
