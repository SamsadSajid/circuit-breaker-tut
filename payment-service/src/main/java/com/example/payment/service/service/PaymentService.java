package com.example.payment.service.service;

import com.example.payment.service.models.requests.PaymentRequest;
import com.example.payment.service.models.responses.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest paymentRequest);
}
