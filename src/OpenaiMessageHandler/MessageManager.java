package OpenaiMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.ExecutionHistory;

public class MessageManager {
    public static void displayMessages(List<ExecutionHistory> messageHistory){
        for (ExecutionHistory message : messageHistory) {
            String role = message.getRole();
            System.out.println("role:" + role);
            String executedCommand = "";
            String executedResultLog = "";
            if(!(message.getCommand().isEmpty())){
                executedCommand = "\n実行されたコマンド\n" + message.getCommand();
            }
            if(!(message.getExecuteResultLog().isEmpty())){
                executedResultLog = "\n実行結果の抜粋\n" + message.getExecuteResultLog();
            }
            String prompt = message.getPrompt() + executedCommand + executedResultLog;
            System.out.println(prompt);
        }
    }
}
