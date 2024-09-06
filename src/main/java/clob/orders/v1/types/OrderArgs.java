package clob.orders.v1.types;

import eth.EthUtils;

public class OrderArgs {
    public String token_id;
    public float price;
    public float size;
    public int side;
    public final int fee_rate_bps = 0;
    public int nonce = 0;
    public String expiration; //UNIX as str OR ordertype
    public String taker = EthUtils.ZERO_ADDRESS;

    public OrderArgs(float price, float size, int side, String token_id, String expiration) {
        this.price = price;
        this.size = size;
        this.side = side;
        this.token_id = token_id;
        this.expiration = expiration;
    }
}
