package com.example.payment.service.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private int code;
    private String message;
}
