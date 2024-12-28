package WebRequestHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenaiRequester {
    public String requestOpenai(String systemPrompt, String userPrompt, String apiKey) {
        try {
            String url = "https://api.openai.com/v1/chat/completions";
            HttpClient client = HttpClient.newHttpClient();
            Map<String, Object> message1 = new HashMap<>();
            message1.put("role", "system");
            message1.put("content", systemPrompt);

            Map<String, Object> message2 = new HashMap<>();
            message2.put("role", "user");
            message2.put("content", userPrompt);

            Map<String, Object> data = new HashMap<>();
            data.put("model", "gpt-4o-mini");
            data.put("messages", new Map[]{message1, message2});

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(data)))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> choices = objectMapper.convertValue(responseMap.get("choices"), new TypeReference<List<Map<String, Object>>>() {});
            Map<String, Object> choice = choices.getFirst();
            Map<String, Object> message = objectMapper.convertValue(choice.get("message"), new TypeReference<Map<String, Object>>() {});
            return (String) message.get("content");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
