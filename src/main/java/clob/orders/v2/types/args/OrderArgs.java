package clob.orders.v2.types.args;

import clob.orders.v2.types.config.OrderOptions;
import eth.EthUtils;

import java.math.BigInteger;

public class OrderArgs {
    public byte[] token_id;
    public float price;
    public float size;
    public int side;
    public final int fee_rate_bps = 0;
    public int nonce = 0;
    public int expiration;
    public String taker = EthUtils.ZERO_ADDRESS;
    public OrderOptions options;

    public OrderArgs(float price, float size, int side, String token_id, OrderOptions options) {
        this.price = price;
        this.size = size;
        this.side = side;
        this.token_id = pad64(new BigInteger(EthUtils.removeZX(token_id)).toByteArray());
        this.options = options;
    }

    public OrderArgs(float price, float size, int side, String token_id, int expiration, OrderOptions options) {
        this.price = price;
        this.size = size;
        this.side = side;
        this.token_id = pad64(new BigInteger(EthUtils.removeZX(token_id)).toByteArray());
        this.expiration = expiration;
        this.options = options;
    }

    private static byte[] pad64(byte[] in) {
        byte[] buffer = new byte[32];
        int offset = 32 - in.length;

        if (offset == 0) return in;
        else if (offset > 0) {
            System.arraycopy(in, 0, buffer, offset, in.length);
        } else {
            System.arraycopy(in, -offset, buffer, 0, buffer.length);
        }
        return buffer;
    }
}