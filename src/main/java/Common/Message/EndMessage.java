package Common.Message;

public class EndMessage extends Message {
    public EndMessage() {
        super(MessageType.END);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
