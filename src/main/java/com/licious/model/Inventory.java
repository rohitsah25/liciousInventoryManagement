package com.licious.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
public class Inventory {


    private int id;
    private int productId;
    private int deliveryCenterId;
    private int quantity;

    public Inventory(int productId, int deliveryCenterId, int quantity) {
        this.id = ThreadLocalRandom.current().nextInt(1000, 100001);
        this.productId = productId;
        this.deliveryCenterId = deliveryCenterId;
        this.quantity = quantity;
    }
}
