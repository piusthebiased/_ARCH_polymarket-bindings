package clob.auth;

import clob.Requests;
import clob.headers.AuthenticationHeaders;
import clob.headers.HeaderSet;
import clob.headers.RequestArgs;
import cryptography.keys.KeyPair;
import cryptography.keys.MalformedPrivateKeyException;
import cryptography.utils.JniLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AuthenticationRequests extends Requests {
    public static APIKeyCredentials deriveAPIKey(KeyPair pair) throws IOException, InterruptedException, URISyntaxException {
        // get L1 headers and request arguments
        HeaderSet headers = AuthenticationHeaders.createL1Headers(pair);
        RequestArgs args = new RequestArgs("GET", "/auth/derive-api-key");

        // build http request
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);
        headers.appendHeaders(builder);

        // send POST request
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // return object
        return APIKeyCredentials.createObjectFromJSON(response.body());
    }

    public static APIKeyCredentials createAPIKey(KeyPair pair) throws IOException, InterruptedException, URISyntaxException {
        // get L1 headers and request arguments
        HeaderSet headers = AuthenticationHeaders.createL1Headers(pair);
        RequestArgs args = new RequestArgs("POST", "/auth/derive-api-key");

        // build http request
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);
        headers.appendHeaders(builder);

        // send POST request
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // return object
        return APIKeyCredentials.createObjectFromJSON(response.body());
    }

    public static void getAPIKey(AuthenticationCredentials credentials) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        // get L2 headers and request arguments
        RequestArgs args = new RequestArgs("GET", "/auth/api-keys");
        HeaderSet headers = AuthenticationHeaders.createL2Headers(credentials, args);

        // build http request
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);
        headers.appendHeaders(builder);

        // send GET request
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static void deleteAPIKey(AuthenticationCredentials credentials) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException {
        // get L2 headers and request arguments
        RequestArgs args = new RequestArgs("DELETE", "/auth/api-key");
        HeaderSet headers = AuthenticationHeaders.createL2Headers(credentials, args);

        // build http request
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);
        headers.appendHeaders(builder);

        // send GET request
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
