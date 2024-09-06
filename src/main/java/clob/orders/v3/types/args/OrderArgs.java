package clob.orders.v3.types.args;

import clob.orders.v3.types.config.OrderOptions;
import eth.EthUtils;

public class OrderArgs {
    // required fees
    public String token_id;
    public float price;
    public float size;
    public String side;

    // optional, but highly recommended fields
    public int fee_rate_bps = 0;
    public int nonce = 0;
    public int expiration = 0;
    public String taker = EthUtils.ZERO_ADDRESS;
    public OrderOptions options;

    //not standard, add signer as a requirement
    public String signer; // assume this is the maker as well
    public int signerType;

    public OrderArgs(float price, float size, String side, String token_id, String signer, int signerType, OrderOptions options) {
        this.price = price;
        this.size = size;
        this.side = side;
        this.token_id = token_id;
        this.options = options;
        this.signer = signer;
        this.signerType = signerType;
    }

    public OrderArgs(float price, float size, String side, String token_id, int expiration, String signer, int signerType, OrderOptions options) {
        this.price = price;
        this.size = size;
        this.side = side;
        this.token_id = token_id;
        this.expiration = expiration;
        this.options = options;
        this.signer = signer;
        this.signerType = signerType;
    }
}
