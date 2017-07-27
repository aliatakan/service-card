package com.example.entity;

/**
 * Created by aliatakan on 16/07/17.
 */

import lombok.Data;

public @Data class Product {
    public long productId;
    public String productTitle;
    public int count;
    public double price;
    public double productTotalPrice;
    public long stock;


}
