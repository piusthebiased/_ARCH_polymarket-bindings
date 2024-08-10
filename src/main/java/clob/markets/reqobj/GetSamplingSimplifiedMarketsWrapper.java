package clob.markets.reqobj;

import clob.PaginatedWrapper;
import clob.markets.SimplifiedMarket;
import com.alibaba.fastjson.JSON;

public class GetSamplingSimplifiedMarketsWrapper extends PaginatedWrapper {
    public SimplifiedMarket[] data;

    public GetSamplingSimplifiedMarketsWrapper(int limit, int count, String next_cursor, SimplifiedMarket[] data) {
        super(limit, count, next_cursor);
        this.data = data;
    }

    public static GetSamplingSimplifiedMarketsWrapper createObjectFromJSON(String json) {
        return JSON.parseObject(json, GetSamplingSimplifiedMarketsWrapper.class);
    }
}
