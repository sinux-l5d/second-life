package Common.Message;

public class CancelledMessage extends Message {
    public CancelledMessage() {
        super(MessageType.CANCELLED);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
