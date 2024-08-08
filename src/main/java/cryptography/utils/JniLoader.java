package cryptography.utils;

import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;

public class JniLoader {
    public final static String ABSOLUTE_PATH = "/home/piuslee/projects/polymarket/client";

    public static void init() {
        Keccak256.init();
        NativeSecp256k1.init();
    }
}
