package com.licious.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


import java.util.concurrent.ThreadLocalRandom;


@Getter
@Setter
@NoArgsConstructor
public class Product {

    private int id;
    private String name;
    private double price;


    public Product(String name, double price) {
        this.id = ThreadLocalRandom.current().nextInt(200, 10001);
        this.name = name;
        this.price = price;
    }
}

