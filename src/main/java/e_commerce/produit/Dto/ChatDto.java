package e_commerce.produit.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto{
    private Long id;
    private String message;
    private String sender;
    private String receiver;


}