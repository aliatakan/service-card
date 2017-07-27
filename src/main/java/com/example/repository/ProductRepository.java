package com.example.repository;

import com.example.entity.Product;

/**
 * Created by aliatakan on 24/07/17.
 */
public interface ProductRepository {
    Product getProduct(long productId);
}
