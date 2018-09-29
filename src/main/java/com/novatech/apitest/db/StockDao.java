package com.novatech.apitest.db;

import com.novatech.apitest.api.SkuStockCheck;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(SkuStockCheckMapper.class)
public interface StockDao {

    @SqlQuery("select sku, description, free from stock s (nolock)")
    List<SkuStockCheck> performStockCheck();

    @SqlQuery("select sku, description, free from stock s (nolock) where sku = ?")
    Optional<SkuStockCheck> performStockCheckOnSku(String sku);

}
