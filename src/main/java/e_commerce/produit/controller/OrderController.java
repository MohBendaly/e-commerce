package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.Order;
import e_commerce.produit.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public  OrderController(OrderService orderService) {
        this.orderService = orderService;
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
    Order saveorder(@RequestBody Order order) {
        return orderService.saveOrder( order);
    }

    @PutMapping("/update")
    Order updateorder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateorder(id, order);
    }


    @DeleteMapping("/delete")
    void deleteorder(@PathVariable Long id) {
        orderService.deleteorder(id);
    }
}
