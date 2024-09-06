package clob.orders.v3;

import clob.orders.v3.types.data.OrderAmount;
// import clob.orders.v3.types.SignedOrder;
import clob.orders.v3.types.args.OrderSide;
import clob.orders.v3.types.config.RoundingConfiguration;

import java.util.Random;

public class OrderUtils {
    public static OrderAmount getOrderAmounts(int side, float size, float price, RoundingConfiguration config) throws Exception {
        float rawPrice = roundNormal(price, config.price);

        if(side == OrderSide.BUY) {
            float rawTakerAmount = roundDown(size, config.size);
            float rawMakerAmount = rawTakerAmount * rawPrice;
            if(decimalPlaces(rawMakerAmount) > config.amount) {
                rawMakerAmount = roundUp(rawTakerAmount, config.amount + 4);
                if (decimalPlaces(rawMakerAmount) > config.amount) {
                    rawMakerAmount = roundUp(rawTakerAmount, config.amount);
                }
            }

            int makerAmount = toTokenDecimals(rawMakerAmount), takerAmount = toTokenDecimals(rawTakerAmount);
            return new OrderAmount(OrderSide.BUY, makerAmount, takerAmount);
        } else if(side == OrderSide.SELL) {
            float rawMakerAmount = roundDown(size, config.size);
            float rawTakerAmount = rawMakerAmount * size;
            if(decimalPlaces(rawTakerAmount) > config.amount) {
                rawTakerAmount = roundUp(rawTakerAmount, config.amount + 4);
                if (decimalPlaces(rawTakerAmount) > config.amount) {
                    rawTakerAmount = roundUp(rawTakerAmount, config.amount);
                }
            }
            int makerAmount = toTokenDecimals(rawMakerAmount), takerAmount = toTokenDecimals(rawTakerAmount);

            return new OrderAmount(OrderSide.SELL, makerAmount, takerAmount);
        } else {
            throw new Exception();
        }
    }

//    public static String orderToJson(SignedOrder order, String api_key, String orderType) {
//        return "{" +
//                    "\"order\": " + order.toString() + "," +
//                    "\"owner\": " + "\"" + api_key + "\"," +
//                    "\"orderType\": " + "\"" + orderType + "\"" +
//                "}";
//    }

    public static byte[] randomSalt() {
        byte[] buffer = new byte[16];
        new Random().nextBytes(buffer);
        return buffer;
    }

    // Helper Functions
    public static float roundDown(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return ((float)Math.floor(x * factor)) / factor;
    }
    public static float roundNormal(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return (float) Math.round(x * factor) / factor;
    }
    public static float roundUp(float x, int significantDigits) {
        int factor = powOf10(significantDigits);
        return ((float)Math.ceil(x * factor)) / factor;
    }
    public static float decimalPlaces(float x) {
        // this is so fucking scuffed -- it takes roughly (245ns \pm 23ns) to execute
        return String.valueOf(x).split("\\.")[1].length();
    }
    public static int toTokenDecimals(float x) {
        float adj = x * 1000000;
        if(decimalPlaces(adj) > 0) adj = roundNormal(x, 0);

        return Math.round(adj);
    }
    private static int powOf10(int num) {
        int x = 10;
        for(int i = 1; i < num; i++) {
            x *= 10;
        }

        return x;
    }
}
