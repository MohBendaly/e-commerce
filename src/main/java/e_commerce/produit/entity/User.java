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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nom;
    String prenom;
    String email;
    String password;
    String role;
    @OneToMany(mappedBy = "user")
    List<Order> orders;
    @OneToOne
    Cart cart;
}
