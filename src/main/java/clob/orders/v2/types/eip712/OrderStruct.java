package clob.orders.v2.types.eip712;

import cryptography.keys.Key;
import cryptography.hash.Keccak256;
import eth.EthUtils;
import eth.eip712.EIP712HashStruct;

import static eth.eip712.EIP712Signature.*;

// This struct will be the hashStruct(message) part of $encode(\mathbb{B}, \mathbb{S})$ [0]
public class OrderStruct implements EIP712HashStruct {
    public byte[] salt; //u256
    public Key maker;
    public Key signer;
    public Key taker;
    public byte[] tokenId; //u256
    public int makerAmount;
    public int takerAmount;
    public int expiration; // TODO: this is a problem for me in 2038
    public int nonce;
    public int feeRateBps;
    public int side;
    public int signatureType;

    public OrderStruct(byte[] salt, Key maker, Key signer, Key taker, byte[] tokenId, int makerAmount, int takerAmount, int expiration, int nonce, int feeRateBps, int side, int signatureType) {
        this.salt = salt;
        this.maker = maker;
        this.signer = signer;
        this.taker = taker;
        this.tokenId = tokenId;
        this.makerAmount = makerAmount;
        this.takerAmount = takerAmount;
        this.expiration = expiration;
        this.nonce = nonce;
        this.feeRateBps = feeRateBps;
        this.side = side;
        this.signatureType = signatureType;
    }

    // EIP-712 functions
    // typeHash(s) : struct s -> keccak256(encodeType(typeOf(str))
    public byte[] typeHash() {
        String type = "Order(u256 salt,address maker,address signer,address taker,uint256 makerAmount,uint256 takerAmount,uint256 expiration,uint256 nonce,uint256 feeRateBps,uint8 side,uint8 signatureType)";
        return Keccak256.digest(EthUtils.asciiBytesFromString(type));
    }

    public byte[] precomputedTypeHash() {
        return new byte[] { (byte) 0x37, (byte) 0x06, (byte) 0xC3, (byte) 0xBE,
                            (byte) 0x13, (byte) 0x9B, (byte) 0xFB, (byte) 0xF1,
                            (byte) 0x50, (byte) 0xB7, (byte) 0xB7, (byte) 0x9C,
                            (byte) 0x59, (byte) 0xA2, (byte) 0xAA, (byte) 0xC0,
                            (byte) 0x64, (byte) 0x27, (byte) 0x91, (byte) 0x2B,
                            (byte) 0x9D, (byte) 0xD7, (byte) 0x67, (byte) 0x31,
                            (byte) 0xF1, (byte) 0xF8, (byte) 0x45, (byte) 0x48,
                            (byte) 0x55, (byte) 0x0B, (byte) 0xE7, (byte) 0xE6
        };
    }
    // encodeData(s) : struct s -> concat(\foreach elements(s))
    public byte[] encodeData() {
        return EthUtils.concat(
                salt, encodeAddress(maker), encodeAddress(signer), encodeAddress(taker),
                tokenId, encodeInt(makerAmount), encodeInt(takerAmount), encodeInt(expiration),
                encodeInt(nonce), encodeInt(feeRateBps), encodeInt(side), encodeInt(signatureType)
        );
    }

    // hashStruct(s) : struct s -> keccak256(typeHash(s) || encodeData(s))
    public byte[] hashStruct() {
        return Keccak256.digest(EthUtils.concat(typeHash(), encodeData()));
    }
    public byte[] precomputedHashStruct() {
        return Keccak256.digest(EthUtils.concat(precomputedTypeHash(), encodeData()));
    }
}