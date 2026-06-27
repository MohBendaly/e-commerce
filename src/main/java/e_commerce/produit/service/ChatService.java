package e_commerce.produit.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import e_commerce.produit.repository.ChatBotRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class ChatService {

    private final ChatBotRepository chatbotRepository;

    @Autowired
    public ChatService(ChatBotRepository chatbotRepository) {
        this.chatbotRepository = chatbotRepository;
    }
    @Value("${deepseek.api.key}")
    private String apiKey;
    private WebClient webClient;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @PostConstruct
    public void init() {
        // Initialisation du WebClient après l'injection de la clé API
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String sendMessage(String userMessage) {

        Map<String, Object> payload = Map.of(
                "model", "deepseek-chat",
                "messages", new Object[]{Map.of("role", "user", "content", userMessage)},
                "temperature", 0.7,
                "max_tokens",500
        );

        try {

            String response = webClient.post()
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class) // Capture la réponse JSON brute
                    .map(this::extractContent) // Décode le contenu JSON pour obtenir le texte de la réponse
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        return Mono.just("Erreur lors de la communication avec le service DeepSeek : " + ex.getMessage());
                    })
                    .block(); // Attention : Peut être remplacé par une approche réactive avec retour d'un Mono<String>

            return response;

        } catch (Exception ex) {

            return "Réponse factice (DeepSeek non contacté) : " + userMessage;
        }
    }

    private String extractContent(String jsonResponse) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                return choicesNode.get(0).path("message").path("content").asText();
            }

            return "Réponse non valide du serveur DeepSeek.";
        } catch (Exception ex) {
            return "Erreur lors de l'analyse de la réponse DeepSeek : " + ex.getMessage();
        }
    }
}
