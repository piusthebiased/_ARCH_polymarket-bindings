package clob.orders.v1.types;

import eth.EthUtils;

public class OrderData {
    public String maker;
    public String taker = EthUtils.ZERO_ADDRESS;
    public String tokenId;
    public String makerAmount;
    public String takerAmount;
    public int side;
    public String feeRateBps;
    public String nonce = "0";
    public int signatureType = Order.Signatures.EOA;

    public OrderData() {}
}
