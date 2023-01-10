package io.ruin.model.shop;

import lombok.Data;

@Data
public class PriceRules {

    public static PriceRules generateDefault() {
        return new PriceRules(100, 25, 10);
    }

    public PriceRules(int sellsAt, int buysAt, int changePer) {
        this.sellsAt = sellsAt;
        this.buysAt = buysAt;
        this.changePer = changePer;
    }

    public PriceRules() {
    }

    public int sellsAt;
    public int buysAt;
    public int changePer;

}