package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.OrderItem;
import e_commerce.produit.service.OrderItemService;
import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    @GetMapping
    List<OrderItem> getAllOrderItems () {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    List<OrderItem> getOrderItems(@PathVariable Long id) {
        return orderItemService.getAllOrderItems();
    }

    @PostMapping("/create")
    List<OrderItem>  saveorderitem(@RequestBody OrderItem orderitem) {
        return orderItemService.saveorderitem( orderitem);
    }

    @PutMapping("/update")
    List<OrderItem> updateorderitem(@PathVariable Long id, @RequestBody OrderItem orderitem) {
        return orderItemService.updateorderitem(id, orderitem);
    }


    @DeleteMapping("/delete")
    void deleteorderitem(@PathVariable Long id) {
        orderItemService.deleteorderitem(id);
    }
}
