package com.example.orderservice.service;

import com.example.orderservice.dto.OrderCheckOutRequest;
import com.example.orderservice.dto.OrderCheckOutResponse;

public interface OrderService {

    OrderCheckOutResponse checkoutOrder(OrderCheckOutRequest orderCheckOutRequest);
}
