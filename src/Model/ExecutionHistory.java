package Model;

public class ExecutionHistory {

    private String role;
    private String prompt;
    private String command;
    private String executeResultLog;
    public ExecutionHistory(String role, String prompt, String command, String executeResultLog){
        this.role = role;
        this.prompt = prompt;
        this.command = command;
        this.executeResultLog = executeResultLog;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
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
