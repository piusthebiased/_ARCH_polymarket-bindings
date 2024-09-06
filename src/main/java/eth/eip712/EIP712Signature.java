package eth.eip712;

import cryptography.hash.Keccak256;
import cryptography.keys.Key;
import cryptography.keys.KeyPair;
import eth.EthUtils;

import java.util.Random;

// This class provides utility functions for EIP-712
public class EIP712Signature {
    public static byte[] HEADER = {0x19, 0x01}; // EIP-712 Structured Message Header [0]


    // ============== EIP-712 encoding functions =============== //
    // bytes b -> keccak256(b)
    public static byte[] encodeBytes(byte[] b) {
        return Keccak256.digest(b);
    }

    // string str -> keccak256(str)
    public static byte[] encodeString(String s) {
        return Keccak256.digest(EthUtils.asciiBytesFromString(s));
    }

    // bool -> u256
    public static byte[] encodeBoolean(boolean bool) {
        byte[] b32 = new byte[32];
        if(bool) b32[31] = 0x01;

        return b32;
    }

    // signed32 int -> signed256 int
    public static byte[] encodeInt(int i) {
        byte[] b32 = new byte[32];
        if(i < 0) b32[0] = (byte) 128;

        b32[28] = (byte) ((i & 0xFF000000) >> 24);
        b32[29] = (byte) ((i & 0x00FF0000) >> 16);
        b32[30] = (byte) ((i & 0x0000FF00) >> 8);
        b32[31] = (byte) ((i & 0x000000FF));

        return b32;
    }

    // address adr -> uint160
    public static byte[] encodeAddress(Key adr) {
        return EthUtils.concat(new byte[12], adr.key());
    }

    // random U256
    public static byte[] randomU256() {
        byte[] buffer = new byte[16];
        new Random().nextBytes(buffer);
        return buffer;
    }
}

/*
 * Notes:
 *
 *   [0] EIP-712 states that all messages (i.e. $\mathbb{B}^{8n} union \mathbb{S}}$) are
 *   prepended with the byte (0x19). One can show that there exists a map s.t. the set of
 *   encoded structs are isomorphic with the set of encoded bit strings (as these strings
 *   extend into infinity). To differentiate structs with bit strings, the second byte is
 *   encoded as 0x01. The chances of collision are 1/256, so it suffices as a detection.
 *
 *   [1]
 */
