package com.driver;

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

        return orders.get(id);

    }

    public void addPartner(String id) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(id);
        deliveryPartners.put(deliveryPartner.getId(), deliveryPartner);
    }

    public DeliveryPartner getPartnerById(String id) {

            return deliveryPartners.get(id);

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
        int maxtime = 0;
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(partnerId.equals(e.getValue())){
                Order order = getOrderById(e.getKey());
                maxtime = Math.max(maxtime,order.getDeliveryTime());
            }
        }
        String hh = String.valueOf(maxtime/60);
        String mm = String.valueOf(maxtime%60);
        if(mm.length() < 2){
            mm+="0";
        }
        return hh+":"+mm;
    }

    public void deletePartnerById(String partnerId) {
        for(String id:deliveryPartners.keySet()){
            if(id.equals(partnerId)){
                deliveryPartners.remove(partnerId);
            }
        }
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(partnerId.equals(e.getValue())){
                orderDeliveryPartnerPair.remove(e.getKey());
            }
        }
    }

    public void deleteOrderById(String orderId) {
        for (String id : orders.keySet()) {
            if (id.equals(orderId)) {
                orders.remove(orderId);
            }
        }
        String pId = orderDeliveryPartnerPair.get(orderId);
        DeliveryPartner deliveryPartner = deliveryPartners.get(pId);
        int totalOrders = deliveryPartners.get(pId).getNumberOfOrders() -1;
        deliveryPartner.setNumberOfOrders(totalOrders);
        for(Map.Entry<String,String> e:orderDeliveryPartnerPair.entrySet()){
            if(orderId.equals(e.getKey())){
                orderDeliveryPartnerPair.remove(e.getKey());
            }
        }
    }
}
