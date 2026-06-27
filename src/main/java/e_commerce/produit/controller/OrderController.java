package e_commerce.produit.controller;

import e_commerce.produit.Dto.OrderDto;
import e_commerce.produit.repository.OrderItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.Order;
import e_commerce.produit.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    public  OrderController(OrderService orderService, OrderItemRepository orderItemRepository) {
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
    }
    @GetMapping
    List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    Order getorderById(@PathVariable Long id) {
        return orderService.getorderById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto dto) {
        try {
            // Vérification
            if (dto.getItems() == null || dto.getItems().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("La commande doit contenir au moins un article");
            }

            Order order = orderService.createOrder(dto);
            return ResponseEntity.ok(order);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la création de la commande");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
