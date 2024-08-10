package clob.eip712;

import cryptography.hash.Keccak256;
import cryptography.utils.HexUtils;
import eth.Address;
import eth.EthUtils;
import eth.eip712.EIP712HashStruct;

import static eth.eip712.EIP712Signature.*;

// This struct will be the hashStruct(message) part of $encode(\mathbb{B}, \mathbb{S})$ [0]
public class ClobAuthStruct implements EIP712HashStruct {
    public Address pubAdr;
    public String timestamp;
    public final int nonce = 0;
    public final String message = "This message attests that I control the given wallet";

    public ClobAuthStruct() {
        this.timestamp = String.valueOf(getTimestamp());
    }
    public ClobAuthStruct(Address pubAdr) {
        this.pubAdr = pubAdr;
        this.timestamp = String.valueOf(getTimestamp());
    }

    // EIP-712 functions
    // typeHash(s) : struct s -> keccak256(encodeType(typeOf(str))
    public byte[] typeHash() {
        String type = "ClobAuth(address address,string timestamp,uint256 nonce,string message)";
        return Keccak256.digest(EthUtils.asciiBytesFromString(type));
    }

    public byte[] precomputedTypeHash() {
        return new byte[] { (byte) 0x52, (byte) 0x57, (byte) 0x8C, (byte) 0x5C,
                            (byte) 0x72, (byte) 0x5A, (byte) 0x28, (byte) 0xA8,
                            (byte) 0x4F, (byte) 0xED, (byte) 0xC8, (byte) 0xC2,
                            (byte) 0x2A, (byte) 0xA4, (byte) 0x79, (byte) 0x47,
                            (byte) 0x82, (byte) 0x29, (byte) 0x42, (byte) 0xF3,
                            (byte) 0x5B, (byte) 0x4D, (byte) 0xC3, (byte) 0x50,
                            (byte) 0xDB, (byte) 0x02, (byte) 0x8E, (byte) 0x45,
                            (byte) 0x32, (byte) 0x0E, (byte) 0x03, (byte) 0x5C
        };
    }
    // encodeData(s) : struct s -> concat(\foreach elements(s))
    public byte[] encodeData() {
        return EthUtils.concat(encodeAddress(pubAdr), encodeString(timestamp), encodeInt(nonce), encodeString(message));
    }

    // hashStruct(s) : struct s -> keccak256(typeHash(s) || encodeData(s))
    public byte[] hashStruct() {
        return Keccak256.digest(EthUtils.concat(typeHash(), encodeData()));
    }
    public byte[] precomputedHashStruct() {
        return Keccak256.digest(EthUtils.concat(precomputedTypeHash(), encodeData()));
    }

    // helping functions
    private static long getTimestamp() {
        return System.currentTimeMillis()/1000;
    }
}
/*
 * Notes:
 *
 *   [0] Unlike ClobAuthDomainStruct, this struct has one variable parameter and one variable
 *   trait: the public address (pubAdr) and timestamp. As a result, I've made the decision
 *   to process this as a class. For purposes of this request, this L1 header will be the
 *   source of the timestamp for the endpoints POST {clob-endpoint}/auth/api-key and
 *   GET {clob-endpoint}/auth/derive-api-key.
 */