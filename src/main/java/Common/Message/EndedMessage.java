package Common.Message;

public class EndedMessage extends Message {
    public EndedMessage() {
        super(MessageType.ENDED);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
