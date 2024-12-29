package OpenaiMessageHandler;

import Exeptions.NoAPIKeyExeption;
import Model.ExecutionHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class OpenaiRequester {
    public String requestOpenai(List<ExecutionHistory> executionHistories, String apiKey) {
        try {
            if (apiKey == null) {
                throw new NoAPIKeyExeption("APIキーが見つかりません。環境変数API_KEYにapiキーが正しくセットされているかを確認してください。");
            }
            String url = "https://api.openai.com/v1/chat/completions";
            HttpClient client = HttpClient.newHttpClient();
            List<Map<String, Object>> messages = new ArrayList<>();
            for (ExecutionHistory message : executionHistories) {
                String role = message.getRole();
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("role", role);
                String executedCommand = "";
                String executedResultLog = "";
                if(!(message.getCommand().isEmpty())){
                    executedCommand = "\n実行されたコマンド\n" + message.getCommand();
                }
                if(!(message.getExecuteResultLog().isEmpty())){
                    executedResultLog = "\n実行結果の抜粋\n" + message.getExecuteResultLog();
                }
                String prompt = message.getPrompt() + executedCommand + executedResultLog;
                messageMap.put("content", prompt);
                messages.add(messageMap);
            }

            // Define the JSON schema for structured output
            Map<String, Object> schemaDefinition = new HashMap<>();
            schemaDefinition.put("type", "object");
            schemaDefinition.put("properties", Map.of("script", Map.of("type", "string")));
            schemaDefinition.put("required", List.of("script"));
            schemaDefinition.put("additionalProperties", false);

            Map<String, Object> data = new HashMap<>();
            data.put("model", "gpt-4o-mini");
            data.put("messages", messages);
            data.put("response_format", Map.of(
                    "type", "json_schema",
                    "json_schema", Map.of(
                            "name", "ExecuteShellResponse",
                            "strict", true,
                            "schema", schemaDefinition
                    )
            ));


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
            String contentString = message.get("content").toString();
            Map<String, Object> content = objectMapper.readValue(contentString, new TypeReference<Map<String, Object>>() {});
            //生成されたスクリプトを返す
            return (String) content.get("script");
        } catch (NoAPIKeyExeption e) {
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getApiKey() {
        //export API_KEY=your_api_key_here
        return System.getenv("API_KEY");
    }
}
