package Server.Command;

import Common.Message.ConnectedMessage;
import Common.Message.Message;
import Server.ConnectionHandler;

public class UserCommand implements Command {
    private final String username;
    private final ConnectionHandler connectionHandler;
    public UserCommand(String username, ConnectionHandler connectionHandler) {
        this.username = username;
        this.connectionHandler = connectionHandler;
    }

    public Message execute() {
        connectionHandler.setUsername(username);
        return new ConnectedMessage();
    }
}
