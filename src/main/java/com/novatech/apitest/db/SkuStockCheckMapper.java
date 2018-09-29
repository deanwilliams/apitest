package com.novatech.apitest.db;

import com.novatech.apitest.api.SkuStockCheck;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkuStockCheckMapper implements RowMapper<SkuStockCheck> {

    @Override
    public SkuStockCheck map(ResultSet rs, StatementContext ctx) throws SQLException {
        final String sku = rs.getString("sku");
        final String description = rs.getString("description");
        final int free = rs.getInt("free");

        return new SkuStockCheck(sku, description, free);
    }

}
