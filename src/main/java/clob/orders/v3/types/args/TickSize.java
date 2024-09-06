package clob.orders.v3.types.args;

public enum TickSize {
    TENTH("0.1"),
    HUNDREDTH("0.01"),
    THOUSANDTH("0.001"),
    TEN_THOUSANDTH("0.0001");

    private final String tickSize;
    private TickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getTickSize() {
        return tickSize;
    }

    public static TickSize fromString(String tickSize) {
        return switch(tickSize) {
            case "0.1" -> TENTH;
            case "0.01" -> HUNDREDTH;
            case "0.001" -> THOUSANDTH;
            case "0.0001" -> TEN_THOUSANDTH;
            default -> throw new IllegalStateException("Unexpected value: " + tickSize);
        };
    }
}
