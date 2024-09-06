package cryptography.sign;

import cryptography.utils.HexUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HMAC_SHA256 {
    private final static String ALGORITHM = "HmacSHA256";

    // TODO: port https://github.com/h5p9sl/hmac_sha256/blob/master/hmac_sha256.c to JNI
    public static String buildHMACSignature(String api_secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] secret_bytes = Base64.getUrlDecoder().decode(api_secret);
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        SecretKeySpec secret_key = new SecretKeySpec(secret_bytes, ALGORITHM);
        sha256_HMAC.init(secret_key);

        return Base64.getUrlEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
    }

    public static String buildHMACSignature(String api_secret, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] secret_bytes = Base64.getUrlDecoder().decode(api_secret);
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        SecretKeySpec secret_key = new SecretKeySpec(secret_bytes, ALGORITHM);
        sha256_HMAC.init(secret_key);

        return Base64.getUrlEncoder().encodeToString(sha256_HMAC.doFinal(message));
    }
}
