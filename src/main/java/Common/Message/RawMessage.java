package Common.Message;

public class RawMessage extends ResponseMessage {
    private String response;

    public RawMessage(String response) {
        super(MessageType.RAW);
        this.response = response;
    }


    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return response;
    }
}
