package e_commerce.produit.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      int id;
      int quantite;
      int prix;
      String status;
    @ManyToOne
      Produit produit;
    @ManyToOne
      Order order;
}
