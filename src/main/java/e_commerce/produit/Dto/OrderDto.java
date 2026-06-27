package e_commerce.produit.Dto;

import e_commerce.produit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<OrderItemDto> items;
    private Double total;
    private String status;
    private Long quantite;
    private Long prix;
    private String date;
    private Long userId;


}
