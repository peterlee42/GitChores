package interface_adapter.git_console;

/**
 * The state for the Git Console View Model.
 */
public class GitConsoleState {
    private String command = "";
    private String lastCommand = "";
    private String lastResponse = "";

    public String getCommand() {return command;}

    public String getLastCommand() {return lastCommand;}

    public String getLastResponse() {return lastResponse;}

    public void setCommand(String command) {this.command = command;}

    public void setLastCommand(String lastCommand) {this.lastCommand = lastCommand;}

    public void setLastResponse(String lastResponse) {this.lastResponse = lastResponse;}
}
