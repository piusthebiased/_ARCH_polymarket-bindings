package clob.orders.v2.types;

public class OrderAmount {
    public int side;
    public int makerAmount;
    public int takerAmount;

    public OrderAmount(int side, int makerAmount, int takerAmount) {
        this.side = side;
        this.makerAmount = makerAmount;
        this.takerAmount = takerAmount;
    }
}
