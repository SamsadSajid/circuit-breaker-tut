package com.example.payment.service.controller;

import com.example.payment.service.models.requests.PaymentRequest;
import com.example.payment.service.models.responses.PaymentResponse;
import com.example.payment.service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/api/v1/payment")
    public PaymentResponse makePayment(@RequestBody PaymentRequest paymentRequest) {

        log.info("Payment request is {}", paymentRequest);
        return paymentService.makePayment(paymentRequest);
    }
}
