package e_commerce.produit.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int quantite;
    String status;
    double total;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id", nullable = false) // Changed to nullable = false
    Cart cart;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id")
    Produit produit;

}