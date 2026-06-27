package e_commerce.produit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatbot")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatBot {
    @Id
    private Long id;
    @Column(name = "message")
    private String message;
    @Column(name = "sender")
    private String sendMessage;


}
