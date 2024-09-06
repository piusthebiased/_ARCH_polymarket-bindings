package clob.orders.v3.types.data;

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
