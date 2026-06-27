package e_commerce.produit.repository;

import e_commerce.produit.entity.ChatBot;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {


    ChatBot findByMessage(String message);

    @Query("SELECT c FROM ChatBot c WHERE c.message LIKE %:message%")
    List<ChatBot> findByMessageLike(@Param("message") String message);

    @NotNull
    List<ChatBot> findAll();
    default String extractReplyFromOllama(String response) {
        return response;
    }
}