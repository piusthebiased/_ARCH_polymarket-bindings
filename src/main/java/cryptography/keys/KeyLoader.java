package cryptography.keys;

import cryptography.utils.HexUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KeyLoader {
    public static KeyPair loadKeys() throws IOException, MalformedPrivateKeyException {
        // import key from system
        String buffer = System.getenv("POLY_PRIVATE_KEY");
        if(buffer == null) {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/private"));
            buffer = br.readLine();
            br.close();
        }

        // verify that key is not malformed
        if(buffer.length() != 64 || !HexUtils.isHex(buffer)) throw new MalformedPrivateKeyException("Invalid private key was given");
        byte[] privateKey = HexUtils.hexStringToByteArray(buffer);

        // automatic public key is already implemented
        return new KeyPair(privateKey);
    }
}
