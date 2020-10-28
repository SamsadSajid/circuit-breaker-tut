package com.example.orderservice.service;

import com.example.orderservice.dto.OrderCheckOutRequest;
import com.example.orderservice.models.responses.PaymentAPIResponse;

public interface PaymentService {

    PaymentAPIResponse makePayment(OrderCheckOutRequest orderCheckOutRequest);
}
