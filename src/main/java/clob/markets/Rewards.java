package clob.markets;

import com.alibaba.fastjson.JSON;

public class Rewards {
    public float min_size;
    public float max_spread;
    public String event_start_date;
    public String event_end_date;
    public float in_game_multiplier;
    public float reward_epoch;

    public Rewards(float min_size, float max_spread, String event_start_date, String event_end_date, float in_game_multiplier, float reward_epoch) {
        this.min_size = min_size;
        this.max_spread = max_spread;
        this.event_start_date = event_start_date;
        this.event_end_date = event_end_date;
        this.in_game_multiplier = in_game_multiplier;
        this.reward_epoch = reward_epoch;
    }

    public static Rewards createObjectFromJSON(String json) {
        return JSON.parseObject(json, Rewards.class);
    }
}
