package clob.auth;

import cryptography.keys.Key;
import cryptography.keys.KeyLoader;
import cryptography.keys.KeyPair;
import cryptography.keys.MalformedPrivateKeyException;

import java.io.IOException;
import java.net.URISyntaxException;

public class AuthenticationCredentials {
    public KeyPair keys;
    public APIKeyCredentials apiCredentials;
    public int signatureType = 1;
    public AuthenticationCredentials(KeyPair keys, APIKeyCredentials apiCredentials) {
        this.keys = keys;
        this.apiCredentials = apiCredentials;
    }

    public static AuthenticationCredentials create() throws MalformedPrivateKeyException, IOException, URISyntaxException, InterruptedException {
        KeyPair keys = KeyLoader.loadKeys();
        APIKeyCredentials apiCredentials = AuthenticationRequests.deriveAPIKey(keys);

        return new AuthenticationCredentials(keys, apiCredentials);
    }
}
