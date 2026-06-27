package e_commerce.produit.controller;

import e_commerce.produit.Dto.OrderDto;
import e_commerce.produit.repository.OrderItemRepository;
import e_commerce.produit.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.OrderItem;
import e_commerce.produit.service.OrderItemService;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderItemController(OrderItemRepository orderItemRepository,OrderRepository orderRepository) {
        this.orderRepository= orderRepository;
        this.orderItemRepository = orderItemRepository;
    }
    @GetMapping("/all")
    List<OrderItem> getAllOrderItems () {
        return orderItemRepository.getAllOrderItems();
    }

    @GetMapping("/{id}")
    List<OrderItem> getOrderItems(@PathVariable Long id) {
        return orderItemRepository.getAllOrderItems();
    }

    @PostMapping("/create")
    public List<OrderItem> saveOrderItem(@RequestBody OrderDto orderDto) {
        // Convertir OrderDto en OrderItem
        OrderItem item = new OrderItem();
        item.setQuantite(Math.toIntExact(orderDto.getQuantite()));   // adaptez les champs
        item.setPrix(BigDecimal.valueOf(orderDto.getPrix()));
        item.setStatus(orderDto.getStatus());

        return orderItemRepository.saveAll(List.of(item));
    }

    @PutMapping("/update")
    List<OrderItem> updateorderitem(@PathVariable Long id, @RequestBody OrderItem orderitem) {
        return orderItemRepository.saveAll(List.of(orderitem));
    }


    @DeleteMapping("/delete")
    void deleteorderitem(@PathVariable Long id) {
        orderItemRepository.deleteById(id);
    }
}
