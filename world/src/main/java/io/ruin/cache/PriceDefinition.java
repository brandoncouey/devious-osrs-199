package io.ruin.cache;

import com.google.gson.annotations.Expose;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class PriceDefinition {

    @Expose
    public List<ItemPrice> prices = new ArrayList<>();

    @RequiredArgsConstructor
    static class ItemPrice {
        @Expose
        public final int id;
        @Expose
        public final String name;
        @Expose
        public final int price;
    }

}
