package e_commerce.produit.controller;


import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.Cart;
import e_commerce.produit.service.CartService;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;


    public CartController( CartService cartService) {
        this.cartService = cartService;

    }
    @GetMapping
    List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping("/create")
    Cart saveCart(@RequestBody Cart Cart) {
        return cartService.saveCart( Cart);
    }

    @PutMapping("/update")
    List<Cart> updateCart(@PathVariable Long id, @RequestBody Cart Cart) {
        return cartService.updateCart(id, Cart);
    }


    @DeleteMapping("/delete")
    void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }
}