package clob.markets;

import clob.markets.types.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MarketEndpointTest {
    @Test  // GET {clob-endpoint}/markets
    void getMarketsOnceTest() throws URISyntaxException, IOException, InterruptedException {
        GetMarketsWrapper wrapper = MarketRequests.getMarkets();
        assertNotNull(wrapper.data);
    }

    @Test  // GET {clob-endpoint}/markets?next_cursor={next_cursor}
    void getMarketsAndRecurse() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<GetMarketsWrapper> wrappers = new ArrayList<>();

        // start pagination
        GetMarketsWrapper currentWrapper = MarketRequests.getMarkets();
        wrappers.add(currentWrapper);
        while (!currentWrapper.next_cursor.equals("LTE=")) {
            System.out.println(currentWrapper.next_cursor);
            currentWrapper = MarketRequests.getMarkets(currentWrapper.next_cursor);
            wrappers.add(currentWrapper);
        }
        System.out.println("Completed data collection");

        // statistics from analyzing wrappers
        int closedMarkets = 0, marketCount = 0, delayedMarkets = 0;
        for(GetMarketsWrapper wrapper : wrappers) {
            marketCount += wrapper.count;
            for(Market market : wrapper.data) {
                if(market.closed) closedMarkets++;
                if(market.seconds_delay > 0) delayedMarkets++;
            }
        }
        System.out.println("Statistics");
        System.out.println("# of markets: " + marketCount);
        System.out.println("# of closed markets: " + closedMarkets);
        System.out.println("# of delayed markets: " + delayedMarkets);
    }

    @Test  // GET {clob-endpoint}/sampling-markets
    void getSamplingMarketsOnceTest() throws URISyntaxException, IOException, InterruptedException {
        GetSamplingMarketsWrapper wrapper = MarketRequests.getSamplingMarkets();
        assertNotNull(wrapper.data);
    }

    @Test  // GET {clob-endpoint}/sampling-markets?next_cursor={next_cursor}
    void getSamplingMarketsAndRecurse() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<GetSamplingMarketsWrapper> wrappers = new ArrayList<>();

        // start pagination
        GetSamplingMarketsWrapper currentWrapper = MarketRequests.getSamplingMarkets();
        wrappers.add(currentWrapper);
        while (!currentWrapper.next_cursor.equals("LTE=")) {
            System.out.println(currentWrapper.next_cursor);
            currentWrapper = MarketRequests.getSamplingMarkets(currentWrapper.next_cursor);
            wrappers.add(currentWrapper);
        }
        System.out.println("Completed data collection");

        // statistics from analyzing wrappers
        int closedMarkets = 0, marketCount = 0, delayedMarkets = 0; float totalUSDC = 0;
        for(GetSamplingMarketsWrapper wrapper : wrappers) {
            marketCount += wrapper.count;
            for(Market market : wrapper.data) {
                if(market.closed) closedMarkets++;
                if(market.seconds_delay > 0) delayedMarkets++;
                for(Rates r : market.rewards.rates) {
                    totalUSDC += r.rewards_daily_rate;
                }
            }
        }
        System.out.println("Statistics");
        System.out.println("# of markets: " + marketCount);
        System.out.println("# of closed markets: " + closedMarkets);
        System.out.println("# of delayed markets: " + delayedMarkets);
        System.out.println("Total USDC: " + totalUSDC);
    }

    @Test  // GET {clob-endpoint}/simplified-markets
    void getSimplifiedMarketsOnceTest() throws URISyntaxException, IOException, InterruptedException {
        GetSimplifiedMarketsWrapper wrapper = MarketRequests.getSimplifiedMarket();
        assertNotNull(wrapper.data);
    }

    @Test  // GET {clob-endpoint}/simplified-markets?next_cursor={next_cursor}
    void getSimplifiedMarketsAndRecurse() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<GetSimplifiedMarketsWrapper> wrappers = new ArrayList<>();

        // start pagination
        GetSimplifiedMarketsWrapper currentWrapper = MarketRequests.getSimplifiedMarket();
        wrappers.add(currentWrapper);
        while (!currentWrapper.next_cursor.equals("LTE=")) {
            System.out.println(currentWrapper.next_cursor);
            currentWrapper = MarketRequests.getSimplifiedMarket(currentWrapper.next_cursor);
            wrappers.add(currentWrapper);
        }
        System.out.println("Completed data collection");

        // statistics from analyzing wrappers
        int closedMarkets = 0, marketCount = 0, delayedMarkets = 0;
        for(GetSimplifiedMarketsWrapper wrapper : wrappers) {
            marketCount += wrapper.count;
            for(SimplifiedMarket market : wrapper.data) {
                if(market.closed) closedMarkets++;
            }
        }
        System.out.println("Statistics");
        System.out.println("# of markets: " + marketCount);
        System.out.println("# of closed markets: " + closedMarkets);
    }

    @Test  // GET {clob-endpoint}/sampling-simplified-markets
    void getSimplifiedAndSamplingMarketsOnceTest() throws URISyntaxException, IOException, InterruptedException {
        GetSamplingSimplifiedMarketsWrapper wrapper = MarketRequests.getSamplingSimplifiedMarket();
        assertNotNull(wrapper.data);
    }

    @Test  // GET {clob-endpoint}/sampling-simplified-markets?next_cursor={next_cursor}
    void getSimplifiedAndSamplingMarketsAndRecurse() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<GetSamplingSimplifiedMarketsWrapper> wrappers = new ArrayList<>();

        // start pagination
        GetSamplingSimplifiedMarketsWrapper currentWrapper = MarketRequests.getSamplingSimplifiedMarket();
        wrappers.add(currentWrapper);
        while (!currentWrapper.next_cursor.equals("LTE=")) {
            System.out.println(currentWrapper.next_cursor);
            currentWrapper = MarketRequests.getSamplingSimplifiedMarket(currentWrapper.next_cursor);
            wrappers.add(currentWrapper);
        }
        System.out.println("Completed data collection");

        // statistics from analyzing wrappers
        int closedMarkets = 0, marketCount = 0, delayedMarkets = 0;
        for(GetSamplingSimplifiedMarketsWrapper wrapper : wrappers) {
            marketCount += wrapper.count;
            for(SimplifiedMarket market : wrapper.data) {
                if(market.closed) closedMarkets++;
                System.out.println(market.condition_id);
            }
        }
        System.out.println("Statistics");
        System.out.println("# of markets: " + marketCount);
        System.out.println("# of closed markets: " + closedMarkets);
    }


    @Test  // GET {clob-endpoint}/markets/{condition_id}
    void getMarketTest() throws URISyntaxException, IOException, InterruptedException {
        Market market = MarketRequests.getMarket("0x12a0cb60174abc437bf1178367c72d11f069e1a3add20b148fb0ab4279b772b2");
        assertNotNull(market.rewards);
    }
}
