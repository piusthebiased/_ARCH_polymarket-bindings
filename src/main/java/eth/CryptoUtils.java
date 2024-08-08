package eth;

import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;

import java.util.Arrays;

public class CryptoUtils {
    public static byte[] ethAddressFromPrivateKey(byte[] pri) {
        // private * G -> public
        long ctx = NativeSecp256k1.contextCreate();
        byte[] pub = NativeSecp256k1.computePubkey(ctx, pri, false);
        NativeSecp256k1.contextCleanup(ctx);

        // compute Keccak(public), note 04 prefix
        byte[] concat_pub = Arrays.copyOfRange(pub, 1, pub.length);
        byte[] digest = Keccak256.digest(concat_pub);

        // split Keccak(public)[-20:]
        return Arrays.copyOfRange(digest, digest.length-20, digest.length);
    }
}
