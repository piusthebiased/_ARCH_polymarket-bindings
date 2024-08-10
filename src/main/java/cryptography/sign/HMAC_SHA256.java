package cryptography.sign;

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
        byte[] secret = Base64.getUrlDecoder().decode(api_secret);
        byte[] encoded_message = message.getBytes(StandardCharsets.UTF_8);

        Mac sha256_hmac = Mac.getInstance(ALGORITHM);
        sha256_hmac.init(new SecretKeySpec(secret, ALGORITHM));

        byte[] mac_data = sha256_hmac.doFinal(encoded_message);
        return new String(Base64.getUrlDecoder().decode(mac_data), StandardCharsets.UTF_8);
    }
}
