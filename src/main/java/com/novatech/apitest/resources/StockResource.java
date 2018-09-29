package com.novatech.apitest.resources;

import com.codahale.metrics.annotation.Timed;
import com.novatech.apitest.api.SkuStockCheck;
import com.novatech.apitest.api.StockCheck;
import com.novatech.apitest.api.User;
import com.novatech.apitest.db.StockDao;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockResource.class);

    private StockDao dao;

    public StockResource(StockDao dao) {
        this.dao = dao;
    }

    @PermitAll
    @GET
    @Timed
    public StockCheck performStockCheck(@Auth User user) {
        LOGGER.info("Performing stock check");

        List<SkuStockCheck> skus = dao.performStockCheck();
        return new StockCheck(skus);
    }

    @PermitAll
    @GET
    @Timed
    @Path("/{sku}")
    public SkuStockCheck performStockCheckOnSku(@Auth User user, @PathParam("sku") String sku) {
        LOGGER.info("Performing stock check on " + sku);

        Optional<SkuStockCheck> skuCheck = dao.performStockCheckOnSku(sku);
        if (!skuCheck.isPresent()) {
            throw new WebApplicationException("SKU " + sku + " does not exist", Response.Status.BAD_REQUEST);
        }
        return skuCheck.get();
    }
}
