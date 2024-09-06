package clob.orders.v1;

import clob.Requests;
import clob.auth.APIKeyCredentials;
import clob.auth.AuthenticationCredentials;
import clob.headers.AuthenticationHeaders;
import clob.headers.RequestArgs;
import clob.orders.v1.types.Order;
import clob.orders.v1.types.OrderAmounts;
import clob.orders.v1.types.RoundConfig;
import clob.quotes.types.Orderbook;
import clob.headers.HeaderSet;
import cryptography.keys.KeyPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OrderRequests extends Requests {
    //
    public static Orderbook postOrder(AuthenticationCredentials credentials) throws URISyntaxException, IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
        RequestArgs args = new RequestArgs("POST", "order");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        // H2 headers
        HeaderSet H2 = AuthenticationHeaders.createL2Headers(credentials, args);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Orderbook.createObjectFromJSON(response.body());
    }

    // Auxiliary Function
    public static OrderAmounts getOrderAmounts(int side, float size, float price, RoundConfig config) throws Exception {
        float rawPrice = roundNormal(price, config.price);

        if(side == Order.Side.BUY) {
            float rawTakerAmount = roundDown(size, config.size);

            float rawMakerAmount = rawTakerAmount * rawPrice;
            if(decimalPlaces(rawMakerAmount) > config.amount) {
                rawMakerAmount = roundUp(rawTakerAmount, config.amount + 4);
                if (decimalPlaces(rawMakerAmount) > config.amount) {
                    rawMakerAmount = roundUp(rawTakerAmount, config.amount);
                }
            }

            int makerAmount = toTokenDecimals(rawMakerAmount), takerAmount = toTokenDecimals(rawTakerAmount);
            return new OrderAmounts(Order.Side.BUY, makerAmount, takerAmount);
        } else if(side == Order.Side.SELL) {
            float rawMakerAmount = roundDown(size, config.size);

            float rawTakerAmount = rawMakerAmount * size;
            if(decimalPlaces(rawTakerAmount) > config.amount) {
                rawTakerAmount = roundUp(rawTakerAmount, config.amount + 4);
                if (decimalPlaces(rawTakerAmount) > config.amount) {
                    rawTakerAmount = roundUp(rawTakerAmount, config.amount);
                }
            }

            int makerAmount = toTokenDecimals(rawMakerAmount);
            int takerAmount = toTokenDecimals(rawTakerAmount);
            return new OrderAmounts(Order.Side.SELL, makerAmount, takerAmount);
        } else {
            throw new Exception();
        }
    }

    // Helper Functions
    public static float roundDown(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return ((float)Math.floor(x * factor)) / factor;
    }

    public static float roundNormal(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return (float) Math.round(x * factor) / factor;
    }

    public static float roundUp(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return ((float)Math.ceil(x * factor)) / factor;
    }

    // this is so fucking scuffed -- it takes roughly (245ns \pm 23ns) to execute
    public static float decimalPlaces(float x) {
        return String.valueOf(x).split("\\.")[1].length();
    }

    public static int toTokenDecimals(float x) {
        float adj = x * 1000000;
        if(decimalPlaces(adj) > 0) adj = roundNormal(x, 0);

        return Math.round(adj);
    }

    private static int powOf10(int num) {
        int x = 10;
        for(int i = 1; i < num; i++) {
            x *= 10;
        }

        return x;
    }

    //private static String orderToJson()
}
