package clob.orders.v2.types;

import clob.auth.AuthenticationCredentials;
import clob.markets.MarketRequests;
import clob.markets.types.Market;
import clob.orders.v1.types.Order;
import clob.orders.v2.OrderBuilder;
import clob.orders.v2.OrderRequests;
import clob.orders.v2.OrderUtils;
import clob.orders.v2.types.args.OrderArgs;
import clob.orders.v2.types.args.OrderType;
import clob.orders.v2.types.args.TickSize;
import clob.orders.v2.types.config.OrderOptions;
import clob.orders.v2.types.eip712.OrderStruct;
import clob.quotes.QuoteRequests;
import clob.quotes.types.Orderbook;
import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;
import java.math.BigInteger;

public class SignedOrder {
    public OrderStruct order;
    public String sig;

    public SignedOrder(OrderStruct order, String sig) {
        this.order = order;
        this.sig = sig;
    }

    public String toString() {
        String side = order.side == 0 ? "BUY" : "SELL";
        return "{" +
                "\"signature\": " + "\"" + sig + "\", " +
                "\"side\": " + "\"" + side + "\", " +
                "\"expiration\": " + "\"" + order.expiration + "\", " +
                "\"nonce\": " + "\"" + order.nonce + "\", " +
                "\"feeRateBps\": " + "\"" + order.feeRateBps + "\", " +
                "\"makerAmount\": " + "\"" + order.makerAmount + "\", " +
                "\"takerAmount\": " + "\"" + order.takerAmount + "\", " +
                "\"tokenId\": " + "\"" + new BigInteger(order.tokenId).toString() + "\"" +
                "}";
    }

    public static void main(String[] args) throws Exception {
        // generate keys
        JniLoader.init();
        AuthenticationCredentials credentials = AuthenticationCredentials.create();

        // prepare market information
        String condition_id = "0x48fc13bd00c0fb187c1c3446bcc09bc3b943d5260ca755618c8bc7c97d6aee94";
        Market market = MarketRequests.getMarket(condition_id);
        String token_id = market.tokens[0].token_id;
        Orderbook book = QuoteRequests.getBook(token_id);

        // send order
        OrderArgs orderArgs = new OrderArgs(
                0.001f,
                1000f,
                Order.Side.BUY,
                token_id,
                new OrderOptions(TickSize.THOUSANDTH, true)
        );

        System.out.println(credentials.apiCredentials.secret);
        System.out.println(credentials.apiCredentials.apiKey);
        System.out.println(credentials.apiCredentials.passphrase);
        System.out.println(HexUtils.bytesToHexString(credentials.keys.publicKey.key()));

        SignedOrder signedOrder = OrderBuilder.createSignedOrder(orderArgs, credentials);
        System.out.println(signedOrder.sig);
        OrderRequests.placeOrder(signedOrder, OrderType.GTC, credentials);
    }
}
