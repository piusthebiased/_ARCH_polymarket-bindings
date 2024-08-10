package clob.rest.headers;

import clob.rest.Requests;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class RequestArgs {
    public String method;
    public String requestPath;
    public String body;

    public RequestArgs(String method, String requestPath) {
        this.method = method;
        this.requestPath = requestPath;
        this.body = null;
    }

    public RequestArgs(String method, String requestPath, String body) {
        this.method = method;
        this.requestPath = requestPath;
        this.body = body;
    }

    public HttpRequest.Builder appendArgs(HttpRequest.Builder builder) throws URISyntaxException {
        builder.uri(new URI(Requests.CLOB_API + requestPath)); // add URI request

        switch (method) { // handle method and body
            case "GET" -> builder.GET();
            case "POST" -> {
                HttpRequest.BodyPublisher body;
                if (this.body == null) body = HttpRequest.BodyPublishers.noBody();
                else body = HttpRequest.BodyPublishers.ofString(this.body);
                builder.POST(body);
            }
            case "DELETE" -> builder.DELETE();
        }

        return builder;
    }
}
