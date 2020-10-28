package com.example.orderservice.api;

import com.example.orderservice.models.requests.PaymentAPIRequest;
import com.example.orderservice.models.responses.PaymentAPIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-api", url = "${payment.api.url}")
public interface PaymentServiceAPI {

    @PostMapping(value = "/api/v1/payment")
    PaymentAPIResponse makePayment(@RequestBody PaymentAPIRequest request);
}
