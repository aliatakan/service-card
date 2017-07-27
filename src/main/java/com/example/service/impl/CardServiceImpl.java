package com.example.service.impl;

import com.example.entity.Card;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.repository.RedisRepository;
import com.example.service.CardService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aliatakan on 22/07/17.
 */

@Service
public class CardServiceImpl implements CardService{
    private static final String CARD_KEY_PREFIX = "card:";
    private static final String PRODUCT_KEY_PREFIX = "product:";

    @Autowired
    private RedisRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCard(String cardId, Product p) {

        //product id, title, price gibi bilgileri db den alıyoruz. DB de olmayan bir ürünün eklenmesini de engellemiş oluyoruz.
        Product product = productRepository.getProduct(p.getProductId());

        String cardKey = CARD_KEY_PREFIX + cardId;
        String productKey = PRODUCT_KEY_PREFIX + p.getProductId() + "_" + cardId;

        //kart var ise sadece "products" alanına yeni ürünü ekliyoruz
        if(repository.exists(cardKey)) {
            repository.hset(cardKey, "cardTotalPrice", Double.toString(Double.parseDouble(repository.hget(cardKey, "cardTotalPrice")) + (product.getPrice() * p.getCount())) );

            if(repository.exists(productKey)){
                //ürün kartta var, o zaman adedini ve total price alanlarını arttır
                repository.hincrby(productKey,"count",p.getCount());
                repository.hset(productKey, "totalPrice", Double.toString(Double.parseDouble(repository.hget(productKey,"totalPrice")) + (p.getCount() * product.getPrice())));
            } else {
                //bu ürün kartta yok ise karta eklenir.
                repository.hmset(productKey, ImmutableMap.<String, String>builder()
                        .put("count", Integer.toString(p.getCount()))
                        .put("title", product.getProductTitle())
                        .put("price", Double.toString(product.getPrice()))
                        .put("totalPrice", Double.toString(product.getPrice() * p.getCount()))
                        .build());

                repository.hset(cardKey, "products", repository.hget(cardKey, "products") + "," + p.getProductId());
            }
        } else {
            //kart yok ise yeni kart açılır
            repository.hmset(cardKey, ImmutableMap.<String, String>builder()
                    .put("products", Long.toString(p.getProductId()))
                    .put("cardTotalPrice", Double.toString(p.getCount() * product.getPrice()))
                    .put("cardDate", LocalTime.now().toString())
                    .build());

            //ürün eklenir
            repository.hmset(productKey, ImmutableMap.<String, String>builder()
                    .put("count", Integer.toString(p.getCount()))
                    .put("title", product.getProductTitle())
                    .put("price", Double.toString(product.getPrice()))
                    .put("totalPrice", Double.toString(product.getPrice() * p.getCount()))
                    .build());
        }
    }

    @Override
    public Card getCard(String id) {
        String cardKey = CARD_KEY_PREFIX + id;

        if(!repository.exists(cardKey)) {
            return null;
        }

        Map<String,String> cardMap = repository.hgetall(cardKey);

        List<String> productIds = Arrays.stream(cardMap.get("products").split(",")).collect(Collectors.toList());
        List<Product> cardProducts = new ArrayList<>();

        productIds.forEach(item-> {
            Map<String,String> p = repository.hgetall(PRODUCT_KEY_PREFIX + item + "_" + id);
            Product product = productRepository.getProduct(Long.parseLong(item)); //ürünün id, title, price alanları DB den çekilip setleniyor
            product.setCount(Integer.parseInt(p.get("count")));
            product.setProductTotalPrice(Double.parseDouble(p.get("totalPrice")));
            cardProducts.add(product);
        });

        Card card = new Card();
        card.setCardTotalPrice(Double.parseDouble(cardMap.get("cardTotalPrice")));
        card.setCartProducts(cardProducts);
        card.setCardId(id);

        return card;
    }


    @Override
    public boolean productExists(long id) {
        Product product = productRepository.getProduct(id);
        if(product == null){
            return false;
        }

        return true;
    }

    @Override
    public boolean productStockExists(long id){
        Product product = productRepository.getProduct(id);
        if(product.getStock() == 0){
            return false;
        }

        return true;
    }
}
