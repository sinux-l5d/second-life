package Server.Command;

import Common.Message.CancelledMessage;
import Common.Message.Message;
import Common.Message.NotFoundMessage;
import Common.Order;
import Common.OrderBook;
import Common.Side;

public class CancelCommand implements Command {
    private final String username;
    private final Side side;
    private final String title;
    private final double price;
    private final OrderBook orderBook;

    public CancelCommand(String username, Side side, String title, double price, OrderBook orderBook) {
        this.username = username;
        this.side = side;
        this.title = title;
        this.price = price;
        this.orderBook = orderBook;
    }

    @Override
    public Message execute() {
        Order order = new Order(username, side, title, price);
        return orderBook.removeOrder(order) ? new CancelledMessage() : new NotFoundMessage();
    }

}
