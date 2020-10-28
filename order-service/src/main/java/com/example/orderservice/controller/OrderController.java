package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderCheckOutRequest;
import com.example.orderservice.dto.OrderCheckOutResponse;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/api/checkout-order")
    public OrderCheckOutResponse checkoutOrder(@RequestBody OrderCheckOutRequest orderCheckOutRequest) {

        log.info("Order check out request {}", orderCheckOutRequest);
        return orderService.checkoutOrder(orderCheckOutRequest);
    }
}
