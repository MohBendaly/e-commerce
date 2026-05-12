package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.CartItem;
import e_commerce.produit.service.CartItemService;
import java.util.List;

@RestController
@RequestMapping("/CartItem")
public class CartItemController {
    private final CartItemService CartItemService;

    public CartItemController(CartItemService CartItemService) {
        this.CartItemService = CartItemService;
    }
    @GetMapping
    List<CartItem> getAllCartItems() {
        return CartItemService.getAllCartItems();
    }

    @GetMapping("/{id}")
    CartItem getCartItemById(@PathVariable Long id) {
        return CartItemService.getCartItemById(id);
    }

    @PostMapping("/create")
    CartItem saveCartItem(@RequestBody CartItem CartItem) {
        return CartItemService.saveCartItem( CartItem);
    }

    @PutMapping("/update")
    CartItem updateCartItem(@PathVariable Long id, @RequestBody CartItem CartItem) {
        return CartItemService.updateCartItem(id, CartItem);
    }


    @DeleteMapping("/delete")
    void deleteCartItem(@PathVariable Long id) {
        CartItemService.deleteCartItem(id);
    }
}
