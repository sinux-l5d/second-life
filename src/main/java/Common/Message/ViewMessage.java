package Common.Message;

public class ViewMessage extends RequestMessage {
    public ViewMessage() {
        super(MessageType.VIEW);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}