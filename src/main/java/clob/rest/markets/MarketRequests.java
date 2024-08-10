package clob.rest.markets;

import clob.markets.Market;
import clob.markets.reqobj.GetMarketsWrapper;
import clob.markets.reqobj.GetSamplingMarketsWrapper;
import clob.markets.reqobj.GetSamplingSimplifiedMarketsWrapper;
import clob.markets.reqobj.GetSimplifiedMarketsWrapper;
import clob.rest.Requests;
import clob.rest.headers.RequestArgs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MarketRequests extends Requests {
    // GET {clob-endpoint}/markets
    public static GetMarketsWrapper getMarkets() throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/markets");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/markets?next_cursor={next_cursor}
    public static GetMarketsWrapper getMarkets(String next_cursor) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/markets?next_cursor=" + next_cursor);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/sampling-markets
    public static GetSamplingMarketsWrapper getSamplingMarkets() throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/sampling-markets");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSamplingMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/sampling-markets?next_cursor={next_cursor}
    public static GetSamplingMarketsWrapper getSamplingMarkets(String next_cursor) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/sampling-markets?next_cursor=" + next_cursor);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSamplingMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/simplified-markets
    public static GetSimplifiedMarketsWrapper getSimplifiedMarket() throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/simplified-markets");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSimplifiedMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/simplified-markets?next_cursor={next_cursor}
    public static GetSimplifiedMarketsWrapper getSimplifiedMarket(String next_cursor) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/simplified-markets?next_cursor=" + next_cursor);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSimplifiedMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/sampling-simplified-markets
    public static GetSamplingSimplifiedMarketsWrapper getSamplingSimplifiedMarket() throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/sampling-markets");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSamplingSimplifiedMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/sampling-simplified-markets?next_cursor={next_cursor}
    public static GetSamplingSimplifiedMarketsWrapper getSamplingSimplifiedMarket(String next_cursor) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/sampling-markets?next_cursor=" + next_cursor);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return GetSamplingSimplifiedMarketsWrapper.createObjectFromJSON(response.body());
    }

    // GET {clob-endpoint}/markets/{condition_id}
    public static Market getMarket(String condition_id) throws URISyntaxException, IOException, InterruptedException {
        RequestArgs args = new RequestArgs("GET", "/market/" + condition_id);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        args.appendArgs(builder);

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Market.createObjectFromJSON(response.body());
    }
}
