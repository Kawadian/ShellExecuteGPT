package OpenaiMessageHandler;

import Exeptions.NoAPIKeyExeption;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenaiRequester {
    public String[] requestOpenai(String systemPrompt, String[] prompts, String apiKey) {
        try {
            if(apiKey==null){
                throw new NoAPIKeyExeption("APIキーが見つかりません。環境変数API_KEYにapiキーが正しくセットされているかを確認してください。");
            }
            List<String> messageHistory = new ArrayList<>();
            String url = "https://api.openai.com/v1/chat/completions";
            HttpClient client = HttpClient.newHttpClient();
            Map<String, Object> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", systemPrompt);

            List<Map<String, Object>> messages = new ArrayList<>();
            messages.add(systemMessage);

            boolean isAssistant = false;
            for (String prompt : prompts) {
                String role = isAssistant ? "assistant" : "user";
                Map<String, Object> message = new HashMap<>();
                message.put("role", role);
                message.put("content", prompt);
                messages.add(message);
                isAssistant = !isAssistant;
                messageHistory.add(prompt);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("model", "gpt-4o-mini");
            data.put("messages", messages);

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
            messageHistory.add((String) message.get("content"));
            return messageHistory.toArray(new String[0]);
        }catch (NoAPIKeyExeption e) {
            System.out.println(e.getMessage());
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getApiKey() {
        //export API_KEY=your_api_key_here
        return System.getenv("API_KEY");
    }
}
