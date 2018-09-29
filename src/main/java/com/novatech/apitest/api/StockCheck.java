package com.novatech.apitest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StockCheck {

    private List<SkuStockCheck> skus;

    @JsonCreator
    public StockCheck(@JsonProperty("skus") List<SkuStockCheck> skus) {
        this.skus = skus;
    }

    public List<SkuStockCheck> getSkus() {
        return skus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockCheck that = (StockCheck) o;
        return Objects.equals(skus, that.skus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skus);
    }
}
