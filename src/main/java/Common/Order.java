package Common;

import Common.Message.OrderMessage;
import Common.Message.MatchMessage;

public class Order {
    private final String username;
    private final Side side;
    private final String title;
    private final double price;

    public Order(String username, Side side, String title, double price) {
        this.username = username;
        this.side = side;
        this.title = title;
        this.price = price;
    }

    public Order(String username, OrderMessage message) {
        this.username = username;
        this.side = message.getSide();
        this.title = message.getTitle();
        this.price = message.getPrice();
    }

    public String getUsername() {
        return username;
    }

    public Side getSide() {
        return side;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public MatchMessage toMatchMessage() {
        return new MatchMessage(side, title, price, username);
    }

    @Override
    public String toString() {
        return side.toString() + "\t" + title + "\t" + price + "\t" + username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Order other)) return false;
        return username.equals(other.username) && side == other.side && title.equals(other.title) && price == other.price;
    }

    @Override
    public int hashCode() {
        // dark magic to avoid colisions, use a prime number
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
