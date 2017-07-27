package com.example.mapper;

import com.example.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aliatakan on 24/07/17.
 */
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product entity = new Product();

        entity.setProductId(rs.getLong("id"));
        entity.setProductTitle(rs.getString("title"));
        entity.setPrice(rs.getDouble("price"));
        entity.setStock((rs.getLong("stock")));

        return entity;
    }
}
