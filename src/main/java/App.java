import clob.headers.AuthenticationHeaders;
import clob.headers.HeaderSet;
import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;
import eth.Address;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        JniLoader.init();

        // load raw key
        byte[] raw = HexUtils.hexStringToByteArray("aa804c0f4372cc9593c238dd5a0c5dd9151f69b6773bd64dc079a735313a8269");
        Address privateKey = new Address(raw);

        // get L1 headers
        HeaderSet headers = AuthenticationHeaders.createL1Headers(privateKey);

        // build http request
        URI uri = new URI("https://clob.polymarket.com/auth/derive-api-key");
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .GET();
        headers.appendHeaders(builder);
        HttpRequest request = builder.build();
        System.out.println(request.headers().toString());

        // send POST request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("response body: " + response.body());
    }
}