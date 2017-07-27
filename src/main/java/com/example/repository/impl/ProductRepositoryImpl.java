package com.example.repository.impl;

import com.example.entity.Product;
import com.example.mapper.ProductRowMapper;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by aliatakan on 24/07/17.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, title, price, stock FROM product WHERE id = :id";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Product getProduct(long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        try{
            return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID, parameters, productRowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
