package com.example.service;

import com.example.entity.Card;
import com.example.entity.Product;

/**
 * Created by aliatakan on 22/07/17.
 */
public interface CardService {
    void addToCard(String cardId, Product p);

    Card getCard(String id);

    boolean productExists(long id);

    boolean productStockExists(long id);
}
