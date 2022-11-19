package com.driver;


import com.driver.DeliveryPartner;
import com.driver.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerService {
    @Autowired
    OrderRepository orderRepository;
    public  void addPartner(String id){
        orderRepository.addPartner(id);
    }
    public DeliveryPartner getPartnerBYId(String id){
        return  orderRepository.getPartnerById(id);
    }
}
