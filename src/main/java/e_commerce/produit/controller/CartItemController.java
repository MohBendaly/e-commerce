package e_commerce.produit.controller;

import e_commerce.produit.Dto.CartitemDto;
import e_commerce.produit.entity.Cart; // Import Cart entity
import e_commerce.produit.entity.Produit;
import e_commerce.produit.repository.CartItemRepository;
import e_commerce.produit.repository.ProduitRepository;
import e_commerce.produit.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.CartItem;
import e_commerce.produit.service.CartItemService;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/CartItem")
@CrossOrigin(origins = "http://localhost:4200")
public class CartItemController {
    private final CartItemService CartItemService;
    private final CartService cartService; // Already injected
    private final ProduitRepository produitRepository;
    private final CartItemRepository cartItemRepository;

    public CartItemController(CartItemService CartItemService, CartService cartService, ProduitRepository produitRepository, CartItemRepository cartItemRepository) {
        this.CartItemService = CartItemService;
        this.cartService = cartService;
        this.produitRepository = produitRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @GetMapping("/{id}")
    CartItem getCartItemById(@PathVariable Long id) {
        return CartItemService.getCartItemById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCartItem(@Validated @RequestBody CartitemDto dto) {
        if (dto.getQuantite() <= 0) {
            return ResponseEntity.badRequest().body("La quantité doit être supérieure à 0");
        }
        try {
            if (dto.getProduitId() == null) {
                return ResponseEntity.badRequest().body("Erreur : L'ID du produit est requis.");
            }
            System.out.println("📦 cartId reçu : " + dto.getCartId());
            System.out.println("=== DEBUG CartItem ===");
            System.out.println("produitId reçu: " + dto.getProduitId());

            Produit produit = produitRepository.findById(dto.getProduitId())
                    .orElseThrow(() -> new ProduitNotFoundException("Produit non trouvé ID : " + dto.getProduitId()));

            System.out.println("Produit trouvé: " + (produit != null ? produit.getNom() : "Aucun"));

            // --- START MODIFICATION ---
            if (dto.getCartId() == null) {
                return ResponseEntity.badRequest().body("Erreur : L'ID du panier (cartId) est requis.");
            }
            Cart cart = cartService.getCartById(dto.getCartId());
            if (cart == null) {
                return ResponseEntity.status(404).body("Panier non trouvé ID : " + dto.getCartId());
            }
            // --- END MODIFICATION ---

            CartItem item = new CartItem();
            item.setQuantite(dto.getQuantite());
            item.setStatus(dto.getStatus());
            item.setTotal(produit.getPrix() * dto.getQuantite());
            item.setProduit(produit);
            item.setCart(cart); // --- MODIFICATION: Set the Cart object ---

            CartItem saved = cartItemRepository.save(item);

            return ResponseEntity.ok(saved);

        } catch (ProduitNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ProduitNotFoundException extends RuntimeException {
        public ProduitNotFoundException(String message) {
            super(message);
        }
    }

    @PutMapping("/update/{id}")
    CartItem updateCartItem(@PathVariable Long id, @RequestBody CartItem CartItem) {
        // You might need similar logic here to ensure the Cart is not null on update
        return CartItemService.updateCartItem(id, CartItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartItemRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}