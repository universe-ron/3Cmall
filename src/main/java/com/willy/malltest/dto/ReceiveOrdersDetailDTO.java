package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveOrdersDetailDTO {
    private Integer ordersDetailId;
    private Integer orderId; //Orders
    private String specId; //ProductSpec
    private int quantity; //OrdersDetail
    private int price; //OrdersDetail
}
