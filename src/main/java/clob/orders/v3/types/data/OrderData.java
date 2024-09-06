package clob.orders.v3.types.data;

import clob.orders.v3.OrderUtils;
import clob.orders.v3.types.args.OrderArgs;
import clob.orders.v3.types.args.OrderSide;
import clob.orders.v3.types.args.Signatures;
import clob.orders.v3.types.config.OrderOptions;
import clob.orders.v3.types.config.RoundingConfiguration;
import eth.EthUtils;

public class OrderData {
    public String maker;
    public String taker = EthUtils.ZERO_ADDRESS;
    public String token_id;
    public String makerAmount;
    public String takerAmount;
    public int side;
    public String feeRateBps;
    public String nonce = "0";
    public String signer;
    public String expiration = "0";
    public int signatureType = Signatures.POLY_PROXY; // PROBABLY A POLYMARKET PROXY

    public OrderData(OrderArgs args, OrderOptions opts) throws Exception {
        RoundingConfiguration rounding = RoundingConfiguration.fromTickSize(opts.tickSize);
        OrderAmount amts = OrderUtils.getOrderAmounts(
                args.side.equals("BUY") ? OrderSide.BUY : OrderSide.SELL,
                args.size, args.price, rounding
        );

        // assume funder is the same as signer
        this.maker = args.signer;
        this.taker = args.taker;
        this.token_id = args.token_id;
        this.makerAmount = String.valueOf(amts.makerAmount);
        this.takerAmount = String.valueOf(amts.takerAmount);
        this.side = amts.side;
        this.feeRateBps = String.valueOf(args.fee_rate_bps);
        this.nonce = String.valueOf(args.nonce);
        this.signer = args.signer;
        this.expiration = String.valueOf(args.expiration);
        this.signatureType = args.signerType;
    }
}
