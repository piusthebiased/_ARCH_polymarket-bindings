package clob.quotes.types;

import com.alibaba.fastjson.JSON;

public class Orderbook {
    public String market;
    public String asset_id;
    public String hash;
    public OrderSummary[] bids;
    public OrderSummary[] asks;

    public Orderbook(String market, String asset_id, String hash, OrderSummary[] bids, OrderSummary[] asks) {
        this.market = market;
        this.asset_id = asset_id;
        this.hash = hash;
        this.bids = bids;
        this.asks = asks;
    }

    public static Orderbook createObjectFromJSON(String json) {
        return JSON.parseObject(json, Orderbook.class);
    }

    public void printOrderbook() {
        for (OrderSummary ask : asks) {
            System.out.println(ask.price + " * " + ask.size);
        }
        System.out.println("spread");
        for(int i = bids.length - 1; i >= 0; i--) {
            System.out.println(bids[i].price + " * " + bids[i].size);
        }
    }
}
