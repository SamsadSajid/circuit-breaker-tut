package com.example.payment.service.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    private String userID;
    private String amount;
}
