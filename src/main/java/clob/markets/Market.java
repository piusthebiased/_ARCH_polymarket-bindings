package clob.markets;

import com.alibaba.fastjson.JSON;

public class Market {
    public String condition_id;
    public String question_id;
    public Token[] tokens;
    public Rewards rewards;
    public String minimum_order_size;
    public String minimum_tick_size;
    public String description;
    public String category;
    public String end_date_iso;
    public String game_start_time;
    public String question;
    public String market_slug;
    public String min_incentive_size;
    public String max_incentive_spread;
    public boolean active;
    public boolean closed;
    public int seconds_delay;
    public String icon;
    public String fpmm;

    public Market(String condition_id, String question_id, Token[] tokens, Rewards rewards, String minimum_order_size, String minimum_tick_size, String description, String category, String end_date_iso, String game_start_time, String question, String market_slug, String min_incentive_size, String max_incentive_spread, boolean active, boolean closed, int seconds_delay, String icon, String fpmm) {
        this.condition_id = condition_id;
        this.question_id = question_id;
        this.tokens = tokens;
        this.rewards = rewards;
        this.minimum_order_size = minimum_order_size;
        this.minimum_tick_size = minimum_tick_size;
        this.description = description;
        this.category = category;
        this.end_date_iso = end_date_iso;
        this.game_start_time = game_start_time;
        this.question = question;
        this.market_slug = market_slug;
        this.min_incentive_size = min_incentive_size;
        this.max_incentive_spread = max_incentive_spread;
        this.active = active;
        this.closed = closed;
        this.seconds_delay = seconds_delay;
        this.icon = icon;
        this.fpmm = fpmm;
    }

    public static Market createObjectFromJSON(String json) {
        return JSON.parseObject(json, Market.class);
    }
}