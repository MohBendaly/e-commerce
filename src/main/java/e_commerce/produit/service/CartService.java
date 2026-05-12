package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.Cart;
import e_commerce.produit.repository.CartRepository;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }
    public Cart saveCart(Cart Cart) {
        return cartRepository.save(Cart);
    }

    public List<Cart> updateCart(Long id, Cart cart) {return cartRepository.saveAll(List.of(cart));
    }

    public void deleteCart(Long id) {
    }
}