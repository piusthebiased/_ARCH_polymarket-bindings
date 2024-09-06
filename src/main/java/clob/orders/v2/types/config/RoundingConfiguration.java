package clob.orders.v2.types.config;

import clob.orders.v2.types.args.TickSize;

public class RoundingConfiguration {
    public int price;
    public int size;
    public int amount;

    public RoundingConfiguration(int price, int size, int amount) {
        this.price = price;
        this.size = size;
        this.amount = amount;
    }

    public static RoundingConfiguration fromTickSize(TickSize ts) {
        return switch (ts) {
            case TENTH -> new RoundingConfiguration(1, 2, 3);
            case HUNDREDTH -> new RoundingConfiguration(2, 2, 4);
            case THOUSANDTH -> new RoundingConfiguration(3, 2, 5);
            case TEN_THOUSANDTH -> new RoundingConfiguration(4, 2, 6);
        };
    }
}