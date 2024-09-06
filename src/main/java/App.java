import clob.auth.APIKeyCredentials;
import clob.auth.AuthenticationCredentials;
import clob.auth.AuthenticationRequests;
import clob.headers.AuthenticationHeaders;
import clob.headers.HeaderSet;
import clob.markets.MarketRequests;
import clob.markets.types.Market;
import clob.orders.v1.types.Order;
import clob.orders.v1.types.OrderArgs;
import clob.quotes.QuoteRequests;
import clob.quotes.types.Orderbook;
import cryptography.keys.KeyLoader;
import cryptography.keys.KeyPair;
import cryptography.keys.MalformedPrivateKeyException;
import cryptography.utils.JniLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, MalformedPrivateKeyException {
        // generate keys
        JniLoader.init();
        AuthenticationCredentials credentials = AuthenticationCredentials.create();

        // prepare market information
        String condition_id = "0x48fc13bd00c0fb187c1c3446bcc09bc3b943d5260ca755618c8bc7c97d6aee94";
        Market market = MarketRequests.getMarket(condition_id);
        String token_id = market.tokens[0].token_id;
        Orderbook book = QuoteRequests.getBook(token_id);
        book.printOrderbook();

        // send order
        OrderArgs order = new OrderArgs(
                0.001f,
                1000f,
                Order.Side.BUY,
                token_id,
                Order.OrderType.GTC
        );

        // update book
        book = QuoteRequests.getBook(token_id);
        book.printOrderbook();
    }
}