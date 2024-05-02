package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrdersDetailDTO {

    private Integer orderId;
    private String userName;
    private Integer ordersDetailId;
    private String productName;
    private String specId; // 4/10新增
    private String color;
    private int quantity;
    private int price;
    private Date orderDate;
    private String orderStatus;
    private String deliverAddress;
    private String recipientPhone;

}
