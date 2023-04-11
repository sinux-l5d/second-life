package Common.Message;

public class ViewMessage extends Message {
    public ViewMessage() {
        super(MessageType.VIEW);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}