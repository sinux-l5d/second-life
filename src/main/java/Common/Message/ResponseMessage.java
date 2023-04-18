package Common.Message;

public abstract class ResponseMessage extends Message {
    public ResponseMessage(MessageType messageType) {
        super(messageType);
    }
}
