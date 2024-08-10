package clob.eip712;

import cryptography.hash.Keccak256;
import eth.EthUtils;

import static eth.eip712.EIP712Signature.*;

// The CLOB Authentication Domain remains the same, this is the domainSeperator [0]
public class ClobAuthDomainStruct {
    // Traits of the domain.
    public final static String NAME = "ClobAuthDomain";
    public final static String VERSION = "1";
    public final static int CHAIN_ID = 137;

    /* FUNCTIONS AND MAPS FOR DOMAIN */
    // typeHash(s) : struct s -> keccak256(encodeType(typeOf(str))
    public static byte[] typeHash() {
        String type = "EIP712Domain(string name,string version,uint256 chainId)";
        return Keccak256.digest(EthUtils.asciiBytesFromString(type));
    }

    // encodeData(s) : struct s -> concat(\foreach elements(s))
    public static byte[] encodeData() {
        return EthUtils.concat(encodeString(NAME), encodeString(VERSION), encodeInt(CHAIN_ID));
    }

    // hashStruct(s) : struct s -> keccak256(typeHash(s) || encodeData(s))
    public static byte[] hashStruct() {
        return Keccak256.digest(EthUtils.concat(typeHash(), encodeData()));
    }

    // hashStruct(s) : const
    public static byte[] precomputedHashStruct() {
        return new byte[] { (byte) 0xCF, (byte) 0xC6, (byte) 0x6B, (byte) 0xE2,
                (byte) 0xA3, (byte) 0xB3, (byte) 0x04, (byte) 0x64,
                (byte) 0xCB, (byte) 0x3B, (byte) 0x58, (byte) 0x83,
                (byte) 0x24, (byte) 0x10, (byte) 0x1F, (byte) 0x66,
                (byte) 0x0C, (byte) 0x9A, (byte) 0x20, (byte) 0x5F,
                (byte) 0xA7, (byte) 0x6E, (byte) 0x8E, (byte) 0x5F,
                (byte) 0x83, (byte) 0xEE, (byte) 0x16, (byte) 0xA5,
                (byte) 0x28, (byte) 0xE1, (byte) 0xC4, (byte) 0xCB
        };
    }
}

/*
 * Notes:
 *
 *   [0] EIP-712 $\S$ Rationale for `typeHash` notes that domains are designed to turn in
 *   to a compile-time constant. Therefore, this trait can (and will be) precomputed with
 *   knowledge before compilation, which justifies naming this as a static class.
 */
