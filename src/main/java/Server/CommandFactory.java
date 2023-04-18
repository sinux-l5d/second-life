package Server;

import Common.Message.*;
import Common.OrderBook;
import Server.Command.*;

public class CommandFactory {
    public static Command fromMessage(Message msg, OrderBook ob, ConnectionHandler connectionHandler) throws NotARequestException, IllegalArgumentException {
        switch (msg.getMessageType()) {
            case USER:
                return new UserCommand(((UserMessage) msg).getUsername(), connectionHandler);
/*
            case ORDER:
                return new OrderCommand(((OrderMessage) msg).getSide(), ((OrderMessage) msg).getSymbol(), ((OrderMessage) msg).getQuantity(), ob);
            case CANCEL:
                return new
*/
            case VIEW:
                return new ViewCommand(ob);
/*
            case END:
                return new
*/

            case CONNECTED:
            case MATCH:
            case CANCELLED:
            case NOT_FOUND:
            case ENDED:
            case RAW:
                throw new NotARequestException(msg);

            default:
                throw new IllegalArgumentException("Invalid message type: " + msg.getMessageType());
        }

    }
}
