package Common;

public enum Side {
    BUY("B"),
    SELL("S");

    private final String side;

    Side(String side) {
        this.side = side;
    }

    public String toString() {
        return side;
    }
}

