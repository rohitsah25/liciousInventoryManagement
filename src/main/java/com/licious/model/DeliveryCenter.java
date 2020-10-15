package com.licious.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryCenter {

    private int id;
    private String address;
    private int location;

    public DeliveryCenter(String address, int location) {
        this.id = ThreadLocalRandom.current().nextInt(200, 10001);
        this.address = address;
        this.location = location;
    }
}
