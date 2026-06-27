package e_commerce.produit.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CartitemDto {

    private int quantite;
    private Double total;
    private String status;
    private Long cartId;
    private Long produitId;


}
