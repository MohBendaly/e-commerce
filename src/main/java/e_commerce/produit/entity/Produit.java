package e_commerce.produit.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nom;
    String description;
    double prix;
    int quantite;
    @OneToMany(mappedBy = "produit")
    List<OrderItem> orderItems;
    @ManyToOne
    Category category;

    public void setCategorie(String categorie) {
    }

    public void setImage(String image) {
    }
}



