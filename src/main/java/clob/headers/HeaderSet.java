package clob.headers;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
public class HeaderSet {
    private Map<String, String> headers;

    public HeaderSet() {
        this.headers = new HashMap<>();
    }

    public HeaderSet addHeader(String key, Object val) {
        headers.put(key, String.valueOf(val));
        return this;
    }

    public HeaderSet removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    public HttpRequest.Builder appendHeaders(HttpRequest.Builder builder) {
        for(String key : headers.keySet()) {
            builder.header(key, headers.get(key));
        }

        return builder;
    }
}
