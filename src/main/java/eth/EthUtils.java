package eth;

import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EthUtils {
    public static byte[] asciiBytesFromString(String s) {
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

    public static byte[] concat(byte[] ...bytes) {
        int concatLength = 0;
        for(byte[] string : bytes) concatLength += string.length;

        int cursor = 0; byte[] concat = new byte[concatLength];
        for(byte[] string : bytes) {
            System.arraycopy(string, 0, concat, cursor, string.length);
            cursor += string.length;
        }

        return concat;
    }
}
