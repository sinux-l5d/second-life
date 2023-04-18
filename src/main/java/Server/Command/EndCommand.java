package Server.Command;

import Common.Message.EndedMessage;
import Common.Message.Message;
import Server.ConnectionHandler;

public class EndCommand implements Command {
    private final ConnectionHandler connectionHandler;
    public EndCommand(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Message execute() {
        connectionHandler.close();
        return new EndedMessage();
    }
}
