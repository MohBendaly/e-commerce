package e_commerce.produit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
 public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String date;
    String status;
    double total;
    @ManyToOne
    User user;
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;
}



