import clob.auth.APIKeyCredentials;
import clob.markets.Market;
import clob.markets.reqobj.GetMarketsWrapper;
import clob.rest.auth.AuthenticationRequests;
import clob.rest.markets.MarketRequests;
import cryptography.keys.KeyLoader;
import cryptography.keys.KeyPair;
import cryptography.keys.MalformedPrivateKeyException;
import cryptography.utils.JniLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, MalformedPrivateKeyException {
        // test markets
        GetMarketsWrapper markets = MarketRequests.getMarkets();
        String conditionID = markets.data[0].condition_id;
        System.out.println(conditionID);

    }
}