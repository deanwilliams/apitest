package com.novatech.apitest.resources;

import com.novatech.apitest.api.SkuStockCheck;
import com.novatech.apitest.api.StockCheck;
import com.novatech.apitest.db.StockDao;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class StockResourceTest {

    private static final StockDao dao = mock(StockDao.class);

    final SkuStockCheck SKU = new SkuStockCheck("TEST-SKU1", "Test SKU Description", 1);
    private static final List<SkuStockCheck> SKUS = new ArrayList<>();
    private static final StockCheck STOCK_CHECK = new StockCheck(SKUS);

    {
        SKUS.add(SKU);
    }

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new StockResource(dao))
            .build();

    @Before
    public void setup() {
        when(dao.performStockCheckOnSku(eq("TEST-SKU1"))).thenReturn(Optional.of(SKU));
        when(dao.performStockCheck()).thenReturn(SKUS);
    }

    @After
    public void tearDown() {
        // we have to reset the mock after each test because of the @ClassRule
        reset(dao);
    }

    @Test
    public void testPerformStockCheck() {
        assertThat(resources.target("/stock").request().get(StockCheck.class))
                .isEqualTo(STOCK_CHECK);
    }

    @Test
    public void testPerformStockCheckBySku() {
        assertThat(resources.target("/stock/TEST-SKU1").request().get(SkuStockCheck.class)).isEqualTo(SKU);
    }

}
