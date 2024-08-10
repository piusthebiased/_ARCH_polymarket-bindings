package clob.rest;

import java.net.http.HttpClient;

public abstract class Requests {
    public static HttpClient client = HttpClient.newHttpClient();
    public static String CLOB_API = "https://clob.polymarket.com";
}
