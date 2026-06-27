package e_commerce.produit.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.util.Map;
import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private RestClient ollamaClient;

    @Value("${ollama.model.name:#{'qwen2.5-coder:1.5b'}}")
    private String modelName;

    @Value("${ollama.client.read-timeout-ms:30000}")
    private int readTimeoutMs;

    @Value("${ollama.client.stream:false}")
    private boolean defaultStream;

    public ChatController() {
    }


    @PostConstruct
    public void init() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(readTimeoutMs);

        this.ollamaClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .requestFactory(factory)
                .build();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, Object> request) {
        String prompt = request.get("message").toString();
        Object streamObj = request.get("stream");
        boolean stream = (streamObj != null) ? Boolean.parseBoolean(streamObj.toString()) : defaultStream;
        System.out.println("Message reçu : " + prompt);
        System.out.println("Longueur : " + prompt.length());
        String response;
        try {
            Map<String, Object> body = Map.of(
                    "model", modelName,
                    "prompt", prompt,
                    "stream", stream,
                    "options", Map.of(
                            "num_ctx", 128,
                            "num_predict", 80,
                            "temperature", 0.1,
                            "top_k", 20,
                            "top_p", 0.8
                    )
            );

            byte[] respBytes = ollamaClient.post()
                    .uri("/api/generate")
                    .body(body)
                    .retrieve()
                    .body(byte[].class);
            response = new String(respBytes, java.nio.charset.StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            String reply = root.path("response").asText();
            return ResponseEntity.ok(Map.of("reply", reply));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("reply", "Erreur de connexion à Ollama : " + e.getMessage()));
        }

    }

    private String extractResponse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.get("response").asText();
        } catch (Exception e) {
            return "Erreur lors de l’analyse de la réponse";
        }
    }
    @GetMapping
    public String testGet() {
        return "Le contrôleur fonctionne (GET) – mais le chat nécessite POST";
    }
}