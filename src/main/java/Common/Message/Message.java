package Common.Message;

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
    public Message fromSerialized(String serialized) {
        String[] parts = serialized.split(":");
        MessageType messageType = MessageType.valueOf(parts[0]);
        String[] params = parts[1].split(",");
        switch (messageType) {
            case USER:
                return new UserMessage(params[0]);
            default:
                throw new IllegalArgumentException("Invalid message type: " + messageType);
        }
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public enum MessageType {
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
