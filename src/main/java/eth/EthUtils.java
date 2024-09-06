package eth;

import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EthUtils {
    public final static String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";
    public final static String ZX = "0x";

    public static byte[] asciiBytesFromString(String s) {
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

    public static String removeZX(String s) {
        if(s.charAt(0) == '0' && s.charAt(1) == 'x') s = s.substring(2);
        return s;
    }

    public static String addZX(String s) {
        if(s.charAt(0) == '0' && s.charAt(1) == 'x') return s;
        else return ZX + s;
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
