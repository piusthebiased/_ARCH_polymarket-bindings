package clob.markets;

import com.alibaba.fastjson.JSON;

public class SimplifiedMarket {
    public String condition_id;
    public Token[] tokens;
    public Rewards rewards;
    public String min_incentive_size;
    public String max_incentive_spread;
    public boolean active;
    public boolean closed;

    public SimplifiedMarket(String condition_id, Token[] tokens, Rewards rewards, String min_incentive_size, String max_incentive_spread, boolean active, boolean closed) {
        this.condition_id = condition_id;
        this.tokens = tokens;
        this.rewards = rewards;
        this.min_incentive_size = min_incentive_size;
        this.max_incentive_spread = max_incentive_spread;
        this.active = active;
        this.closed = closed;
    }

    public static SimplifiedMarket createObjectFromJSON(String json) {
        return JSON.parseObject(json, SimplifiedMarket.class);
    }
}