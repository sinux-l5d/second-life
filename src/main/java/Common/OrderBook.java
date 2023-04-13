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

    public List<Order> getBuyOrders(String title) {
        return orders.stream()
                .filter(order -> order.getSide() == Side.BUY && order.getTitle().equals(title))
                .toList();
    }

    public List<Order> getSellOrders(String title) {
        return orders.stream()
                .filter(order -> order.getSide() == Side.SELL && order.getTitle().equals(title))
                .toList();
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
