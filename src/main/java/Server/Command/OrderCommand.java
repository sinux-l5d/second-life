// OrderCommand.java
package Server.Command;

import Common.Message.MatchMessage;
import Common.Message.Message;
import Common.Message.OrderMessage;
import Common.Message.RawMessage;
import Common.Order;
import Common.Side;
import Server.Server;
import Common.OrderBook;

public class OrderCommand implements Command {
    private final String username;
    private final Side side;
    private final String title;
    private final double price;
    private final OrderBook orderBook;
    private final Server server;

    public OrderCommand(String username, Side side, String title, double price, OrderBook orderBook, Server server) {
        this.username = username;
        this.side = side;
        this.title = title;
        this.price = price;
        this.orderBook = orderBook;
        this.server = server;
    }

    @Override
    public Message execute() {
        Order order = new Order(username, side, title, price);

        Order match = orderBook.matchOrder(order);
        if (match != null) {
            // When an order is matched, broadcast the message to all clients
            Message matchMessage = match.toMatchMessage();
            server.broadcast(matchMessage);
            // don't send twice
            return new RawMessage("");
        } else {
            orderBook.addOrder(order);
            return new RawMessage(orderBook.toString());
        }
    }
}
