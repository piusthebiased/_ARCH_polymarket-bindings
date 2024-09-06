package cryptography.hash;

import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;

public class Keccak256 {
    public static native void keccak256(byte[] input, int start, int len, byte[] output);

    public static void init() {
        System.load(JniLoader.ABSOLUTE_PATH + "/libs/libkeccak256.so");
    }

    public static String digestString(String s) {
        return HexUtils.bytesToHexString(digest(HexUtils.hexStringToByteArray(s)));
    }

    public static byte[] digest(byte[] inbuf) {
        byte[] output = new byte[32];
        keccak256(inbuf, 0, inbuf.length, output);
        return output;
    }

    public static byte[] digest(byte[] inbuf, int start, int len) {
        if (start < 0 || start + len > inbuf.length) {
            throw new IllegalArgumentException("invalid start and len");
        }
        byte[] output = new byte[32];
        keccak256(inbuf, start, len, output);
        return output;
    }
}
