package com.driver.Repository;

import com.driver.DeliveryPartner;
import com.driver.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    HashMap<String, Order> orders ;
    HashMap<String, DeliveryPartner> deliveryPartners;
    HashMap<String,String> orderDeliveryPartnerPair;
    int noOfOrders;

    public OrderRepository() {
        this.orders = new HashMap<>();
        this.deliveryPartners = new HashMap<>();
        this.orderDeliveryPartnerPair = new HashMap<>();
        this.noOfOrders = 0;
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public Order getOrderById(String id) {
        if(orders.containsKey(id)){
            return orders.get(id);
        }
        return null;
    }

    public void addPartner(String id) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(id);
        deliveryPartners.put(deliveryPartner.getId(), deliveryPartner);
    }

    public DeliveryPartner getPartnerById(String id) {
        if(deliveryPartners.containsKey(id)){
            return deliveryPartners.get(id);
        }
        return  null;

    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        DeliveryPartner deliveryPartner = deliveryPartners.get(partnerId);
        int totalOrders = deliveryPartners.get(partnerId).getNumberOfOrders();
        totalOrders++;
        deliveryPartner.setNumberOfOrders(totalOrders);

        orderDeliveryPartnerPair.put(orderId, partnerId);
    }

    public int orderCountByPartnerId(String partnerId) {
        if(deliveryPartners.containsKey(partnerId)){
            return deliveryPartners.get(partnerId).getNumberOfOrders();
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orders = new ArrayList<>();
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(partnerId.equals(e.getValue())){
                orders.add(e.getKey());
            }
        }
        return orders;
    }

    public List<String> getAllOrders() {
        List<String> orderList = new ArrayList<>();
        for(Map.Entry<String,Order> e:orders.entrySet()){
            orderList.add(e.getKey());
        }
        return orderList;
    }

    public Integer getCountOfUnassignedOrders() {

        return orders.size() - orderDeliveryPartnerPair.size();

    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int delivery ;
        int undeliveredCount = 0;
        String hh =  time.substring(0,2);
        String mm = time.substring(3);
        delivery =  Integer.valueOf(hh) *60 + Integer.valueOf(mm);
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(partnerId.equals(e.getValue())){
                Order order = getOrderById(e.getKey());
                if(order.getDeliveryTime()> delivery){
                    undeliveredCount++;
                }
            }
        }
        return undeliveredCount;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String LastDeliveryTime ="";
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(partnerId.equals(e.getValue())){
                Order order = getOrderById(e.getKey());
                LastDeliveryTime = String.valueOf(order.getDeliveryTime());
            }
        }
        return LastDeliveryTime;
    }

    public void deletePartnerById(String partnerId) {
        for(String id:deliveryPartners.keySet()){
            if(id.equals(partnerId)){
                deliveryPartners.remove(partnerId);
            }
        }
    }

    public void deleteOrderById(String orderId) {
        for (String id : orders.keySet()) {
            if (id.equals(orderId)) {
                orders.remove(orderId);
            }
        }

        for(String id: orderDeliveryPartnerPair.keySet()){
            if(id==orderId){
                orderDeliveryPartnerPair.remove(orderId);
            }
        }
    }
}
