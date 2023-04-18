package Server;

import Common.Message.*;
import Common.OrderBook;
import Server.Command.*;

public class CommandFactory {
    public static Command fromMessage(Message msg, OrderBook ob, ConnectionHandler connectionHandler, Server server) throws NotARequestException, IllegalArgumentException {
        return switch (msg.getMessageType()) {
            case USER -> new UserCommand(((UserMessage) msg).getUsername(), connectionHandler);
            case ORDER -> {
                OrderMessage orderMessage = (OrderMessage) msg;
                yield new OrderCommand(connectionHandler.getUsername(), orderMessage.getSide(), orderMessage.getTitle(), orderMessage.getPrice(), ob, server);
            }
            case CANCEL -> {
                CancelMessage cm = (CancelMessage) msg;
                yield new CancelCommand(connectionHandler.getUsername(), cm.getSide(), cm.getTitle(), cm.getPrice(), ob);
            }
            case VIEW -> new ViewCommand(ob);
            case END -> new EndCommand(connectionHandler);
            case CONNECTED, MATCH, CANCELLED, NOT_FOUND, ENDED, RAW -> throw new NotARequestException(msg);
            default -> throw new IllegalArgumentException("Invalid message type: " + msg.getMessageType());
        };

    }
}
