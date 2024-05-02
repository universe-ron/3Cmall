package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReceiveOrdersAndDetailDTO {

    private Long userId; //user
    private Date orderDate; //order
    private String paymentMethod; //order
    private String orderStatus; //order
    private Date deliverDate; //order
    private Date pickupDate; //order
    private String deliverAddress; //order
    private String recipientName; //order
    private String recipientPhone; //order
    private Date paymentTime; //order
    private Integer orderId; //order
    private String specId; //productSpec
    private int quantity; //orderDetail
    private int price; //orderDetail

}
