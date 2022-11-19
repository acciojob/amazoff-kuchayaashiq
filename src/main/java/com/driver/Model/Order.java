package com.driver.Model;

import lombok.Builder;
import lombok.Data;

@Data

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String hh =  deliveryTime.substring(0,2);
        String mm = deliveryTime.substring(3);
        this.deliveryTime =  Integer.valueOf(hh) *60 + Integer.valueOf(mm);

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
