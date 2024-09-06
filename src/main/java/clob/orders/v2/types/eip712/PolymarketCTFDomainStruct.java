package clob.orders.v2.types.eip712;

import clob.orders.v2.types.config.ContractConfiguration;
import cryptography.hash.Keccak256;
import cryptography.keys.Key;
import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;
import eth.EthUtils;

import static eth.eip712.EIP712Signature.*;

public class PolymarketCTFDomainStruct {
    public final static String NAME = "Polymarket CTF Exchange";
    public final static String VERSION = "1";
    public final static int CHAIN_ID = 137;
    public Key CONTRACT_ADDRESS;

    public PolymarketCTFDomainStruct(ContractConfiguration config) {
        CONTRACT_ADDRESS = new Key(HexUtils.hexStringToByteArray(EthUtils.removeZX(config.exchange)));
    }

    /* FUNCTIONS AND MAPS FOR DOMAIN */
    // typeHash(s) : struct s -> keccak256(encodeType(typeOf(str))
    public byte[] typeHash() {
        String type = "EIP712Domain(string name,string version,uint256 chainId,address verifyingContract)";
        return Keccak256.digest(EthUtils.asciiBytesFromString(type));
    }

    // encodeData(s) : struct s -> concat(\foreach elements(s))
    public byte[] encodeData() {
        return EthUtils.concat(encodeString(NAME), encodeString(VERSION), encodeInt(CHAIN_ID), encodeAddress(CONTRACT_ADDRESS));
    }

    // hashStruct(s) : struct s -> keccak256(typeHash(s) || encodeData(s))
    public byte[] hashStruct() {
        return Keccak256.digest(EthUtils.concat(typeHash(), encodeData()));
    }


    // hashStruct(s) : const
    public static byte[] precomputedHashStruct() {
        return new byte[] { (byte) 0x1A, (byte) 0x57, (byte) 0x3E, (byte) 0x36,
                            (byte) 0x17, (byte) 0xC7, (byte) 0x84, (byte) 0x03,
                            (byte) 0xB5, (byte) 0xB4, (byte) 0xB8, (byte) 0x92,
                            (byte) 0x82, (byte) 0x79, (byte) 0x92, (byte) 0xF0,
                            (byte) 0x27, (byte) 0xB0, (byte) 0x3D, (byte) 0x4E,
                            (byte) 0xAF, (byte) 0x57, (byte) 0x00, (byte) 0x48,
                            (byte) 0xB8, (byte) 0xEE, (byte) 0x8C, (byte) 0xDD,
                            (byte) 0x84, (byte) 0xD1, (byte) 0x51, (byte) 0xBE
        };
    }

    public static byte[] precomputedHashStructNegativeRisk() {
        return new byte[] { (byte) 0x68, (byte) 0x92, (byte) 0xCC, (byte) 0x26,
                (byte) 0xB4, (byte) 0xC4, (byte) 0x83, (byte) 0x0B,
                (byte) 0x07, (byte) 0x8D, (byte) 0xD0, (byte) 0x22,
                (byte) 0xC8, (byte) 0xEE, (byte) 0x2C, (byte) 0x4E,
                (byte) 0x3A, (byte) 0x12, (byte) 0xBC, (byte) 0x8F,
                (byte) 0x21, (byte) 0xF7, (byte) 0xA0, (byte) 0x3B,
                (byte) 0x09, (byte) 0x85, (byte) 0xC9, (byte) 0x89,
                (byte) 0x25, (byte) 0xF2, (byte) 0x96, (byte) 0x68
        };
    }
}
