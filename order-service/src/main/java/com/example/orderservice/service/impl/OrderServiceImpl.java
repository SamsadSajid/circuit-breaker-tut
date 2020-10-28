package com.example.orderservice.service.impl;

import com.example.orderservice.dto.OrderCheckOutRequest;
import com.example.orderservice.dto.OrderCheckOutResponse;
import com.example.orderservice.models.responses.PaymentAPIResponse;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;

    @Override
    public OrderCheckOutResponse checkoutOrder(OrderCheckOutRequest orderCheckOutRequest) {

        // 1. Entry this order to the order table
        log.info("Dumping into db {}", orderCheckOutRequest);
        // 2. Call Payment API
        PaymentAPIResponse paymentAPIResponse = paymentService.makePayment(orderCheckOutRequest);
        // 3. If payment is successful return success response
        var orderCheckOutResponse = OrderCheckOutResponse.builder();

        if (paymentAPIResponse.getCode() == 333) {
            orderCheckOutResponse
                    .code(paymentAPIResponse.getCode())
                    .message(paymentAPIResponse.getMessage());
        } else {
            orderCheckOutResponse
                    .code(555)
                    .message("Payment failed");
        }
        // 4. Else return fail response
        // 5. Before return publish a topic to send a notification
        return orderCheckOutResponse.build();
    }

    // TODO:
    //  1. Decrypt Token
    //  2. Get userID
    //  3. Call User service to get this User --- dorkar ache???
    private String getUserId(String token) {

        return UUID.randomUUID().toString();
    }
}
