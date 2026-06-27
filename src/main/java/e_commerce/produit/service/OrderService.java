package e_commerce.produit.service;

import e_commerce.produit.Dto.OrderDto;
import e_commerce.produit.Dto.OrderItemDto;
import e_commerce.produit.entity.OrderItem;
import e_commerce.produit.entity.Produit;
import e_commerce.produit.entity.User;
import e_commerce.produit.repository.OrderItemRepository;
import e_commerce.produit.repository.OrderRepository;
import e_commerce.produit.repository.ProduitRepository;
import e_commerce.produit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProduitRepository produitRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProduitRepository produitRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getorderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->  new RuntimeException("Order Not Found"));}

    public Order updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setTotal(order.getTotal());
                    existingOrder.setDate(order.getDate());
                    // Mettez à jour les autres champs nécessaires ici
                    return orderRepository.save(existingOrder);
                })
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'id " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order createOrder(OrderDto dto) {
        // Vérification
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("La commande doit contenir des articles");
        }

        // Logs de debug
        System.out.println("📥 DTO reçu : " + dto);
        System.out.println("👤 UserId reçu : " + dto.getUserId());

        // Récupérer l'utilisateur
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Créer la commande
        Order order = new Order();
        order.setUser(user);
        order.setTotal(dto.getTotal());  // ✅ total est Double
        order.setDate(LocalDateTime.now().toString());
        order.setStatus("EN_ATTENTE");

        // Quantité totale (convertir en Long)
        int totalQuantite = dto.getItems().stream()
                .mapToInt(OrderItemDto::getQuantite)
                .sum();
        order.setQuantite((long) totalQuantite);  // ✅ quantite est Long

        // Sauvegarder la commande
        Order savedOrder = orderRepository.save(order);

        // Créer les OrderItems
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto itemDto : dto.getItems()) {
            // Vérifier produitId
            if (itemDto.getProduitId() == null) {
                System.err.println("⚠️ produitId null pour un item, ignoré");
                continue;
            }

            // Vérifier quantite
            if (itemDto.getQuantite() <= 0) {
                System.err.println("⚠️ Quantité <= 0 pour produit " + itemDto.getProduitId() + ", ignoré");
                continue;
            }

            // Vérifier prix (Double)
            if (itemDto.getPrix() == null || itemDto.getPrix().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le prix doit être supérieur à 0");
            }

            // Vérifier que le produit existe
            Produit produit = produitRepository.findById(itemDto.getProduitId())
                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + itemDto.getProduitId()));

            // Créer l'OrderItem
            OrderItem item = new OrderItem();
            item.setQuantite(itemDto.getQuantite());
            item.setPrix(itemDto.getPrix());
            item.setProduit(produit);
            item.setOrder(savedOrder);
            item.setStatus("EN_ATTENTE");
            orderItems.add(item);
        }

        // Sauvegarder tous les OrderItems
        if (!orderItems.isEmpty()) {
            orderItemRepository.saveAll(orderItems);
            savedOrder.setOrderItems(orderItems);
        }

        return orderRepository.save(savedOrder);
    }

}


   