package Server.Command;

import Common.Message.Message;

public class NotARequestException extends Exception {
    public NotARequestException(Message msg) {
        super(msg.getMessageType() + " is not a request message");
    }
}
