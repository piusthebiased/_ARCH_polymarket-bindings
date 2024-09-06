package clob.orders.v2;

import clob.Requests;
import clob.auth.APIKeyCredentials;
import clob.auth.AuthenticationCredentials;
import clob.headers.AuthenticationHeaders;
import clob.headers.HeaderSet;
import clob.headers.RequestArgs;
import clob.orders.v2.types.SignedOrder;
import clob.orders.v2.types.args.OrderType;
import clob.quotes.types.Orderbook;
import cryptography.keys.KeyPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OrderRequests extends Requests {
    // POST {clob-endpoint}/order/
    public static void placeOrder(SignedOrder order, String orderType, AuthenticationCredentials credentials) throws URISyntaxException, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
        // create request
        HttpRequest.Builder builder = HttpRequest.newBuilder();

        // arguments and body
        String body = OrderUtils.orderToJson(order, credentials.apiCredentials.apiKey, orderType);
        RequestArgs args = new RequestArgs("POST", "/order", body);
        args.appendArgs(builder);

        // L2 headers
        HeaderSet L2 = AuthenticationHeaders.createL2Headers(credentials, args);
        L2.appendHeaders(builder);

        // make request
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
