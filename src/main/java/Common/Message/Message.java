package Common.Message;

import Common.Side;
import Server.Command.WrongParamsException;

public abstract class Message {
    private final MessageType messageType;

    /**
     * Creates a new message with the given type.
     * Intended to be used by subclasses.
     *
     * @param messageType The type of the message.
     */
    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Creates a new message from a serialized string.
     * Factory method.
     *
     * @param serialized The serialized string.
     * @return The message.
     */
    public static Message fromSerialized(String serialized) throws IllegalArgumentException, WrongParamsException {
        String[] parts = serialized.split(":");
        MessageType messageType = MessageType.valueOf(parts[0]);
        String[] params = new String[0];
        if (parts.length > 1) {
            params = parts[1].split(",");
        }
        switch (messageType) {
            case USER -> {
                checkParams(params, 1);
                return new UserMessage(params[0]);
            }
            case ORDER -> {
                checkParams(params, 3);
                return new OrderMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]));
            }
            case CANCEL -> {
                checkParams(params, 3);
                return new CancelMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]));
            }
            case VIEW -> {
                checkParams(params, 0);
                return new ViewMessage();
            }
            case END -> {
                checkParams(params, 0);
                return new EndMessage();
            }
            case CONNECTED -> {
                checkParams(params, 0);
                return new ConnectedMessage();
            }
            case MATCH -> {
                checkParams(params, 4);
                return new MatchMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]), params[3]);
            }
            case CANCELLED -> {
                checkParams(params, 0);
                return new CancelledMessage();
            }
            case NOT_FOUND -> {
                checkParams(params, 0);
                return new NotFoundMessage();
            }
            case ENDED -> {
                checkParams(params, 0);
                return new EndedMessage();
            }
            default -> throw new IllegalArgumentException("Invalid message type: " + messageType);
        }
    }

    private static void checkParams(String[] params, int expected) throws WrongParamsException {
        if (params.length != expected)
            throw new WrongParamsException("Invalid number of parameters, " + expected + " expected, " + params.length + " given");
    }

    public MessageType getMessageType() {
        return messageType;
    }

    abstract public String toString();

    protected String serialize(String[] params) {
        return params.length == 0 ? messageType.toString() : messageType + ":" + String.join(",", params);
    }

    public enum MessageType {
        RAW, // Response only
        USER,
        ORDER,
        CANCEL,
        VIEW,
        END,
        CONNECTED,
        MATCH,
        CANCELLED,
        NOT_FOUND,
        ENDED
    }
}
