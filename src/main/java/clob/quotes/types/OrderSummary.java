package clob.quotes.types;

import com.alibaba.fastjson.JSON;

public class OrderSummary {
    public String price;
    public String size;

    public OrderSummary(String price, String size) {
        this.price = price;
        this.size = size;
    }

    public static OrderSummary createObjectFromJSON(String json) {
        return JSON.parseObject(json, OrderSummary.class);
    }
}
