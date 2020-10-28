package com.example.orderservice.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentAPIResponse {

    private int code;
    private String message;
}
