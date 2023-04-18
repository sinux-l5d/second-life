package Server.Command;

import Common.Message.Message;
import Common.Message.RawMessage;
import Common.OrderBook;

public class ViewCommand implements Command {
    private final OrderBook ob;
    public ViewCommand(OrderBook ob) {
        this.ob = ob;
    }

    public Message execute() {
        return new RawMessage(ob.toString());
    }
}
