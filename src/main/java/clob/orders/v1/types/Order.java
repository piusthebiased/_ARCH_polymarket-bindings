package clob.orders.v1.types;

import cryptography.keys.Key;
import eth.eip712.EIP712HashStruct;

public class Order implements EIP712HashStruct {
    // fields
    public byte[] salt; // U256
    public Key maker;
    public Key signer;
    public Key taker;
    public byte[] tokenId; // U256
    public byte[] makerAmount; // U256
    public byte[] takerAmount; // U256
    public byte[] expiration; // U256
    public byte[] nonce;
    public byte[] feeRate;



    // EIP712 Methods
    @Override
    public byte[] typeHash() {
        return new byte[0];
    }

    @Override
    public byte[] encodeData() {
        return new byte[0];
    }

    @Override
    public byte[] hashStruct() {
        return new byte[0];
    }

    // classes and datatypes
    public static class Side {
        public final static int BUY = 0, SELL = 1;
    }

    public static class OrderType {
        public final static String GTC = "GTC", FOC = "FOC", GTD = "GTD";
    }

    public static class Signatures {
        public final static int EOA = 0, POLY_PROXY = 1, POLY_GNOSIS_SAFE = 2;
    }
}
