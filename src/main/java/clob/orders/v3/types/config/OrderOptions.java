package clob.orders.v3.types.config;

import clob.markets.types.Market;
import clob.orders.v3.types.args.TickSize;

public class OrderOptions {
    public TickSize tickSize;
    public boolean negativeRisk;
    public String signer;

    public OrderOptions(TickSize tickSize, boolean negativeRisk) {
        this.tickSize = tickSize;
        this.negativeRisk = negativeRisk;
    }

    public OrderOptions fromMarket(Market market) {
        return new OrderOptions(TickSize.fromString(market.minimum_tick_size), market.neg_risk);
    }
}
