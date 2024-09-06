package clob.orders.v1.types;

public class RoundConfig {
    public int price;
    public int size;
    public int amount;

    public RoundConfig(int price, int size, int amount) {
        this.price = price;
        this.size = size;
        this.amount = amount;
    }
}
