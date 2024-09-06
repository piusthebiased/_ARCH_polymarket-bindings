package clob.markets.types;

import com.alibaba.fastjson.JSON;

public class Rates {
    public String asset_address;
    public float rewards_daily_rate;

    public Rates(String asset_address, float rewards_daily_rate) {
        this.rewards_daily_rate = rewards_daily_rate;
        this.asset_address = asset_address;
    }

    public static Rates createObjectFromJSON(String json) {
        return JSON.parseObject(json, Rates.class);
    }
}
