package clob.markets;

import com.alibaba.fastjson.JSON;

public class Token {
    public String token_id;
    public String outcome;

    public Token(String token_id, String outcome) {
        this.token_id = token_id;
        this.outcome = outcome;
    }

    public static Token createObjectFromJSON(String json) {
        return JSON.parseObject(json, Token.class);
    }
}
