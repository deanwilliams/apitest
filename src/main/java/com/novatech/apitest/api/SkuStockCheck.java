package com.novatech.apitest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import java.util.Objects;

@JsonSnakeCase
public class SkuStockCheck {

    private final String sku;
    private final String description;
    private final int free;

    @JsonCreator
    public SkuStockCheck(@JsonProperty("sku") final String sku,
                         @JsonProperty("description") final String description,
                         @JsonProperty("free") final int free) {
        this.sku = sku;
        this.description = description;
        this.free = free;
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public int getFree() {
        return free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkuStockCheck that = (SkuStockCheck) o;
        return free == that.free &&
                Objects.equals(sku, that.sku) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, description, free);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SkuStockCheck{");
        sb.append("sku='").append(sku).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", free=").append(free);
        sb.append('}');
        return sb.toString();
    }
}
