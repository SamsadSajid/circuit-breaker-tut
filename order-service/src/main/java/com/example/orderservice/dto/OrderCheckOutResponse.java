package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCheckOutResponse {

    private int code;
    private String message;
}
