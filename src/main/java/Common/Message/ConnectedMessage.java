package Common.Message;

public class ConnectedMessage extends Message {

    public ConnectedMessage() {
        super(MessageType.CONNECTED);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
