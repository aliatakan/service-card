package com.example.controller;

import com.example.entity.Card;
import com.example.entity.Product;
import com.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by aliatakan on 16/07/17.
 */

@RestController
@RequestMapping(value = "/sepet")
public class CardController {

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity addToCard(@RequestBody Product product, @PathVariable("id") String cardId){
        //eklenmeye çalışılan ürün DB de var mı ?
        if(cardService.productExists(product.getProductId())){
            if(cardService.productStockExists(product.getProductId())) {
                cardService.addToCard(cardId, product);
                return new ResponseEntity(HttpStatus.CREATED);
            }else {
                return new ResponseEntity("stock out", HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity("no product", HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Card> getCard(@PathVariable String id){
        return new ResponseEntity<Card>(cardService.getCard(id), HttpStatus.OK);
    }

}
