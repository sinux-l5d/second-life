package Common.Message;

import Common.Side;

public abstract class Message {
    private final MessageType messageType;

    /**
     * Creates a new message with the given type.
     * Intended to be used by subclasses.
     * @param messageType The type of the message.
     */
    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Creates a new message from a serialized string.
     * Factory method.
     * @param serialized The serialized string.
     * @return The message.
     */
    public static Message fromSerialized(String serialized) throws IllegalArgumentException {
        String[] parts = serialized.split(":");
        MessageType messageType = MessageType.valueOf(parts[0]);
        String[] params = parts[1].split(",");
        switch (messageType) {
            case USER:
                return new UserMessage(params[0]);
            case ORDER:
                return new OrderMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]));
            case CANCEL:
                return new CancelMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]));
            case VIEW:
                return new ViewMessage();
            case END:
                return new EndMessage();
            case CONNECTED:
                return new ConnectedMessage();
            case MATCH:
                return new MatchMessage(Side.valueOf(params[0]), params[1], Double.parseDouble(params[2]), params[3]);
            case CANCELLED:
                return new CancelledMessage();
            case NOT_FOUND:
                return new NotFoundMessage();
            case ENDED:
                return new EndedMessage();
            default:
                throw new IllegalArgumentException("Invalid message type: " + messageType);
        }
    }

    public MessageType getMessageType() {
        return messageType;
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

    abstract public String toString();

    protected String serialize(String[] params) {
        return params.length == 0 ? messageType.toString() : messageType + ":" + String.join(",", params);
    }
}
