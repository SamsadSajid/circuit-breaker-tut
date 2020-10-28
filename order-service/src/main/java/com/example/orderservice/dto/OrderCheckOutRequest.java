package com.example.orderservice.dto;

import com.example.orderservice.models.Orders;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCheckOutRequest {

    private String userID;
    private List<Orders> orders;
    private String amount;
}
