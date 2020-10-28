package com.example.orderservice.service.impl;

import com.example.orderservice.api.PaymentServiceAPI;
import com.example.orderservice.dto.OrderCheckOutRequest;
import com.example.orderservice.dto.OrderCheckOutResponse;
import com.example.orderservice.models.requests.PaymentAPIRequest;
import com.example.orderservice.models.responses.PaymentAPIResponse;
import com.example.orderservice.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceAPI paymentServiceAPI;

    @Override
    @HystrixCommand(fallbackMethod = "defaultMakePayment")
    public PaymentAPIResponse makePayment(OrderCheckOutRequest orderCheckOutRequest) {

        return paymentServiceAPI.makePayment(getPaymentAPIRequest(orderCheckOutRequest));
    }

    private PaymentAPIRequest getPaymentAPIRequest(OrderCheckOutRequest orderCheckOutRequest) {

        return PaymentAPIRequest.builder()
                .userID(orderCheckOutRequest.getUserID())
                .amount(orderCheckOutRequest.getAmount())
                .build();
    }

    public PaymentAPIResponse defaultMakePayment(OrderCheckOutRequest orderCheckOutRequest) {

        log.info("Hystrix Fall back payment method called");

        return PaymentAPIResponse.builder()
                .code(555)
                .message("Payment failed")
                .build();
    }
}
