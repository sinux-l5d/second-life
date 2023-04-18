package Common.Message;

public class NotFoundMessage extends ResponseMessage {
    public NotFoundMessage() {
        super(MessageType.NOT_FOUND);
    }

    @Override
    public String toString() {
        return serialize(new String[]{});
    }
}
