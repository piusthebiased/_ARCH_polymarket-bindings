package clob.auth;

import com.alibaba.fastjson.JSON;

public class APIKeyCredentials {
    public String apiKey;
    public String secret;
    public String passphrase;

    public APIKeyCredentials(String apiKey, String secret, String passphrase) {
        this.apiKey = apiKey;
        this.secret = secret;
        this.passphrase = passphrase;
    }

    public static APIKeyCredentials createObjectFromJSON(String json) {
        return JSON.parseObject(json, APIKeyCredentials.class);
    }
}
