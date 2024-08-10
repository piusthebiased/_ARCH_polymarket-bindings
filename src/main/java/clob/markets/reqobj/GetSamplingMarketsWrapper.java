package clob.markets.reqobj;

import clob.PaginatedWrapper;
import clob.markets.Market;
import com.alibaba.fastjson.JSON;

public class GetSamplingMarketsWrapper extends PaginatedWrapper {
    public Market[] data;

    public GetSamplingMarketsWrapper(int limit, int count, String next_cursor, Market[] data) {
        super(limit, count, next_cursor);
        this.data = data;
    }

    public static GetSamplingMarketsWrapper createObjectFromJSON(String json) {
        return JSON.parseObject(json, GetSamplingMarketsWrapper.class);
    }
}
