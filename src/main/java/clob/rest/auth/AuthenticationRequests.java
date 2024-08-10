package clob.rest.auth;

import clob.auth.APIKeyCredentials;
import clob.rest.Requests;
import clob.rest.headers.AuthenticationHeaders;
import clob.rest.headers.HeaderSet;
import clob.rest.headers.RequestArgs;
import cryptography.keys.KeyPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
}
