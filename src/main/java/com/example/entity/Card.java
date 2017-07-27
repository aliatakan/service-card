package com.example.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by aliatakan on 16/07/17.
 */
public @Data class Card {
    public String cardId;
    public List<Product> cartProducts;
    public double cardTotalPrice;
    public Date cardDate;
}
