package e_commerce.produit.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nom;
    String description;
    double prix;
    int quantite;
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    List<OrderItem> orderItems = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = true)
    @JsonIgnore
    private Category category;

}
