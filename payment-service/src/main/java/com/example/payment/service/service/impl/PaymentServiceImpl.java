package com.example.payment.service.service.impl;

import com.example.payment.service.models.requests.PaymentRequest;
import com.example.payment.service.models.responses.PaymentResponse;
import com.example.payment.service.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        log.info("Dump to payment DB {}", paymentRequest);

        return PaymentResponse.builder()
                .code(333)
                .message("Payment is successful")
                .build();
    }
}
