package com.novatech.apitest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class SkuStockCheckTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final SkuStockCheck sku = new SkuStockCheck("TEST-SKU1", "Test SKU Description", 1);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/sku.json"), SkuStockCheck.class));

        assertThat(MAPPER.writeValueAsString(sku)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final SkuStockCheck sku = new SkuStockCheck("TEST-SKU1", "Test SKU Description", 1);

        assertThat(MAPPER.readValue(fixture("fixtures/sku.json"), SkuStockCheck.class))
                .isEqualTo(sku);
    }
}
