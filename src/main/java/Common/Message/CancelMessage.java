package Common.Message;

public class CancelMessage extends Message {
    private final OrderMessage.Side side;
    private final String title;
    private final double price;

    public CancelMessage(OrderMessage.Side side, String title, double price) {
        super(MessageType.CANCEL);
        this.side = side;
        this.title = title;
        this.price = price;
    }

    public OrderMessage.Side getSide() {
        return side;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serialize(new String[]{side.toString(), title, Double.toString(price)});
    }
}

