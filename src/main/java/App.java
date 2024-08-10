import clob.auth.APIKeyCredentials;
import clob.rest.auth.AuthenticationRequests;
import cryptography.keys.KeyLoader;
import cryptography.keys.KeyPair;
import cryptography.keys.MalformedPrivateKeyException;
import cryptography.utils.JniLoader;
import cryptography.keys.Key;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, MalformedPrivateKeyException {
        JniLoader.init();

        // load raw key
        KeyPair pair = KeyLoader.loadKeys();
        APIKeyCredentials apiKey = AuthenticationRequests.deriveAPIKey(pair);

    }
}