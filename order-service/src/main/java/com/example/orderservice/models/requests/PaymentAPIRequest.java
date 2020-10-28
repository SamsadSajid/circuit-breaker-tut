package com.example.orderservice.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentAPIRequest {

    private String userID;
    private String amount;
}
