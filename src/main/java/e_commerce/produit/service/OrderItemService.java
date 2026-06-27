package e_commerce.produit.service;

import e_commerce.produit.Dto.OrderDto;
import e_commerce.produit.Dto.OrderItemDto;
import e_commerce.produit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.OrderItem;
import e_commerce.produit.repository.OrderItemRepository;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {
   private OrderRepository orderRepository;
   private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository,OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem saveOrderItemFromDto(OrderItemDto dto) {
        OrderItem item = new OrderItem();
        item.setQuantite(dto.getQuantite());
        item.setPrix(dto.getPrix());
        item.setStatus(dto.getStatus());
        return orderItemRepository.save(item);
    }

    public List<OrderItem> saveOrderItem(OrderDto orderDto) {
        // Convertir DTO → Entité
        OrderItem item = new OrderItem();
        item.setQuantite(Math.toIntExact(orderDto.getQuantite()));
        item.setPrix(BigDecimal.valueOf(orderDto.getPrix()));
        item.setStatus(orderDto.getStatus());

        // Sauvegarder une liste d'un seul élément
        return orderItemRepository.saveAll(List.of(item));  // ← List.of(item)
    }

    public List<OrderItem> updateorderitem(Long id, OrderItem orderitem) { return orderItemRepository.saveAll(List.of(orderitem));
    }

    public void deleteorderitem(Long id) {
    }
}
