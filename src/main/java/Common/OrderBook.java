package Common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBook {
    private final List<Order> orders;
    private static OrderBook instance;

    private OrderBook() {
        orders = new ArrayList<>();
    }

    public static OrderBook getInstance() {
        if (instance == null) {
            instance = new OrderBook();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    /**
     * Returns a list of orders that are compatible with the given order
     */
    public List<Order> matchingOrders(Order order) {
        // sideWanted is the opposite of the side of the order that is being matched
        Side sideWanted = order.getSide() == Side.BUY ? Side.SELL : Side.BUY;


        return orders.stream()
                .filter(o -> o.getSide() == sideWanted && o.getTitle().equals(order.getTitle()))
                .filter(o -> priceCompatible(o.getPrice(), order.getPrice()))
                .toList();
    }

    /**
     * Returns the first order that is compatible with the given order, and removes it from the order book
     */
    public Order matchOrder(Order order) {
        List<Order> matchingOrders = matchingOrders(order);
        if (matchingOrders.isEmpty()) {
            return null;
        } else {
            Order match = matchingOrders.get(0);
            orders.remove(match);
            return match;
        }
    }

    private boolean priceCompatible (double price1, double price2) {
        return Math.abs(price1 - price2) <= 5;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public String toString() {
        return orders.stream()
                .map(Order::toString)
                .collect(Collectors.joining("\n"));
    }
}
