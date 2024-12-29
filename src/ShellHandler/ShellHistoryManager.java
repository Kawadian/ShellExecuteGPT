package ShellHandler;

import Model.ExecutionHistory;
import OpenaiMessageHandler.MessageManager;
import OpenaiMessageHandler.OpenaiRequester;

import java.util.List;

public class ShellHistoryManager {
    public List<ExecutionHistory> manageShellAndRequests(List<ExecutionHistory> executionHistoryList, String apiKey, String scriptPath){
        boolean isExit = false;
        while(!isExit) {
            OpenaiRequester openaiRequester = new OpenaiRequester();
            String openaiText = openaiRequester.requestOpenai(executionHistoryList, apiKey);
            if(openaiText.contains("exit")){
                return executionHistoryList;
            }
            String stdOut = "";
            if (openaiText != null) {
                System.out.println(openaiText);
                MessageManager.displayMessages(executionHistoryList);
                stdOut = ScriptExecuter.executeScript(scriptPath, openaiText, false);
            } else {
                System.out.println("openai apiへのリクエスト送信中にエラーが発生しました。");
            }
            executionHistoryList.add(new ExecutionHistory("assistant", "", openaiText, ""));
            executionHistoryList.add(new ExecutionHistory("user", "",stdOut,""));
        }
        return executionHistoryList;
    }
}
