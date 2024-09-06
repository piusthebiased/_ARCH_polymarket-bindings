package clob.quotes.types;

import com.alibaba.fastjson.JSON;

public class BookParams {
    public String token_id;
    public String side;

    public BookParams(String token_id, String side) {
        this.token_id = token_id;
        this.side = side;
    }

    public static BookParams createObjectFromJSON(String json) {
        return JSON.parseObject(json, BookParams.class);
    }
}
