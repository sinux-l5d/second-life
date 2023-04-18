package Common.Message;

public class UserMessage extends RequestMessage {
    private final String username;

    public UserMessage(String username) {
        super(MessageType.USER);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return serialize(new String[]{username});
    }
}
