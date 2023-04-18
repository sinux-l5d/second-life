package Common.Message;

public class EndMessage extends RequestMessage {
    public EndMessage() {
        super(MessageType.END);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
