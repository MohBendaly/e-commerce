package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.CartItem;
import e_commerce.produit.repository.CartItemRepository;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository CartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository CartItemRepository) {
        this.CartItemRepository = CartItemRepository;
    }

    public List<CartItem> getAllCartItems() {
        return CartItemRepository.findAll();
    }

    public CartItem saveCartItem(CartItem CartItem) {
        return CartItemRepository.save(CartItem);
    }

    public CartItem getCartItemById(Long id) {
        return CartItemRepository.findById(id).orElse(null);
    }

    public CartItem updateCartItem(Long id, CartItem cartItem) {
        return CartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long id) {
    }
}
