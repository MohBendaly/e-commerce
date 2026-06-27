package e_commerce.produit.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private int quantite; // Changed from Double quantite to int quantity
    private BigDecimal prix; // Changed from Double prix to BigDecimal price
    private String status;

    // Corrected getter name
    public Long getProduitId() {
        return productId;
    }
}