package clob.orders.v1.types;

public class CreateOrderOptions {
    public String tick_size;  //["0.1", "0.01", "0.001", "0.0001"]
    public boolean neg_risk = false;

    public CreateOrderOptions(String tick_size, boolean neg_risk) {
        this.tick_size = tick_size;
        this.neg_risk = neg_risk;
    }
}
