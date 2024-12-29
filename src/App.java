
import Model.ExecutionHistory;
import OpenaiMessageHandler.MessageManager;
import OpenaiMessageHandler.OpenaiRequester;
import ShellHandler.ScriptExecuter;
import ShellHandler.ShellHistoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void app(){
        String scriptPath = "./tempScript.sh";
        OpenaiRequester openaiRequester = new OpenaiRequester();
        String apiKey = openaiRequester.getApiKey();
        Scanner scanner = new Scanner(System.in);
        System.out.println("要望を入力");
        String userPrompt = scanner.nextLine();
        String systemPrompt = "これからあなたにはユーザーの入力、場合によっては追加でコマンドの実行結果が渡されます。あなたはユーザーの入力に答えるためにシェルスクリプトを生成してください。これ以上実行するコマンドがないと考えられる場合は「exit」と出力してください";
        List<ExecutionHistory> executionHistories = new ArrayList<>();
        executionHistories.add(new ExecutionHistory("system", systemPrompt, "", ""));
        executionHistories.add(new ExecutionHistory("user", userPrompt,"", ""));
        ShellHistoryManager shellHistoryManager = new ShellHistoryManager();
        shellHistoryManager.manageShellAndRequests(executionHistories,apiKey,scriptPath);
    }
}
