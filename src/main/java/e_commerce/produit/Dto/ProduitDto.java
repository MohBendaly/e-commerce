package e_commerce.produit.Dto;

import e_commerce.produit.entity.Produit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDto {
    private Long id;
    private String nom;
    private String description;
    private double prix;
    private int quantite;
    private String imageUrl;

    @NotNull
    private Long categoryId;

    public static ProduitDto fromEntity(Produit produit) {

        if (produit == null) return null;
        ProduitDto dto = new ProduitDto();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrix());
        dto.setQuantite(produit.getQuantite());
        dto.setImageUrl(produit.getImageUrl());
        if (produit.getCategory() != null) {
            dto.setCategoryId(produit.getCategory().getId());
        }
        return dto;
    }

}

