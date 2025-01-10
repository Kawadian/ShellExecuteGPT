package ShellHandler;

import Model.ExecutionHistory;
import OpenaiMessageHandler.MessageManager;
import OpenaiMessageHandler.OpenaiRequester;

import java.util.List;

public class ShellHistoryManager {
    public List<ExecutionHistory> manageShellAndRequests(List<ExecutionHistory> executionHistoryList, String apiKey, String scriptPath) throws Exception{
        boolean isExit = false;
        while(!isExit) {
            OpenaiRequester openaiRequester = new OpenaiRequester();
            String openaiText = openaiRequester.requestOpenai(executionHistoryList, apiKey);
            if(openaiText.contains("exit")){
                return executionHistoryList;
            }
            String stdOut = "";
            System.out.println(openaiText);
            stdOut = ScriptExecuter.executeScript(scriptPath, openaiText, false);
            if(stdOut.isEmpty()){
                return executionHistoryList;
            }
            executionHistoryList.add(new ExecutionHistory("assistant", "", openaiText, ""));
            executionHistoryList.add(new ExecutionHistory("user", "",stdOut,""));
        }
        return executionHistoryList;
    }
}
