package clob.markets.types;

import com.alibaba.fastjson.JSON;

public class Rewards {
    public float min_size;
    public float max_spread;
    public Rates[] rates;

    public Rewards(float min_size, float max_spread, Rates[] rates) {
        this.min_size = min_size;
        this.max_spread = max_spread;
        this.rates = rates;
    }

    public static Rewards createObjectFromJSON(String json) {
        return JSON.parseObject(json, Rewards.class);
    }
}
