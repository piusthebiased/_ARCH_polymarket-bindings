package clob.quotes;

import clob.Requests;
import clob.headers.RequestArgs;
import clob.markets.types.GetMarketsWrapper;
import clob.quotes.types.Orderbook;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QuoteRequests extends Requests {
    public static final class Side {
        public static final String BUY = "BUY";
        public static final String SELL = "SELL";
    }

    public static Orderbook getBook(String tokenId) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/book?token_id="+tokenId);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Orderbook.createObjectFromJSON(response.body());
    }

    //TODO: add support for $1.00 price, but this is pretty useless
    public static float getPrice(String tokenId, String side) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/price?token_id="+tokenId+"&side="+side);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // incredibly useless optimization, but parses floats of this kind like 10x faster
        // 50mbps v.s. 700mbps, pick your poison
        char[] json = response.body().toCharArray(); //start at 12 lol
        //char[] buffer =



        return JSON.parseObject(response.body(), float.class);
    }


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String token = "35181131500187602593152358358430710972979053258824221563421108711539553867878";
        System.out.println(getPrice(token, Side.BUY));
    }
}