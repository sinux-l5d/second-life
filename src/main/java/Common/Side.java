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

    public static Side fromString(String side) throws IllegalArgumentException {
        for (Side s : Side.values())
            if (s.side.equals(side))
                return s;
        throw new IllegalArgumentException("Invalid side: " + side);
    }
}

