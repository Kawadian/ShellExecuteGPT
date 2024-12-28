package Model;

public class ExecutionHistory {

    private String userPrompt;
    private String command;
    private String executeResultLog;
    public ExecutionHistory(String userPrompt, String command, String executeResultLog){
        this.userPrompt = userPrompt;
        this.command = command;
        this.executeResultLog = executeResultLog;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getExecuteResultLog() {
        return executeResultLog;
    }

    public void setExecuteResultLog(String executeResultLog) {
        this.executeResultLog = executeResultLog;
    }
}
