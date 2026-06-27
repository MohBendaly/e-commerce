package e_commerce.produit.Dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CategoryDto {
    private String name;
    private String description;

    @NotNull
    private Long produitId;
}
