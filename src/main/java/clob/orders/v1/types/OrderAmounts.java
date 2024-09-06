package clob.orders.v1.types;

public class OrderAmounts {
    public int side;
    public int makerAmount;
    public int takerAmount;

    public OrderAmounts(int side, int makerAmount, int takerAmount) {
        this.side = side;
        this.makerAmount = makerAmount;
        this.takerAmount = takerAmount;
    }
}
