package OpenaiMessageHandler;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    public static void displayMessages(String[] messageHistory){
        for (String message : messageHistory){
            boolean isAssistant = false;
            String role = isAssistant ? "assistant" : "user";
            System.out.println(role + ":" + message);
            isAssistant = !isAssistant;
        }
    }
}
