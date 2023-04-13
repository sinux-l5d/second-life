package Common.Message;

import Common.Side;

public class MatchMessage extends Message {
    private final Side side;
    private final String title;
    private final double price;
    private final String counterparty;

    public MatchMessage(Side side, String title, double price, String counterparty) {
        super(MessageType.MATCH);
        this.side = side;
        this.title = title;
        this.price = price;
        this.counterparty = counterparty;
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

    public String getCounterparty() {
        return counterparty;
    }

    @Override
    public String toString() {
        return serialize(new String[]{side.toString(), title, Double.toString(price), counterparty});
    }
}
