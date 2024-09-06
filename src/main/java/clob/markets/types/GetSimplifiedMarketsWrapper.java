package clob.markets.types;

import clob.PaginatedWrapper;
import com.alibaba.fastjson.JSON;

public class GetSimplifiedMarketsWrapper extends PaginatedWrapper {
    public SimplifiedMarket[] data;

    public GetSimplifiedMarketsWrapper(int limit, int count, String next_cursor, SimplifiedMarket[] data) {
        super(limit, count, next_cursor);
        this.data = data;
    }

    public static GetSimplifiedMarketsWrapper createObjectFromJSON(String json) {
        return JSON.parseObject(json, GetSimplifiedMarketsWrapper.class);
    }
}
