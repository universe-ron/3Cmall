package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReceiveOrdersDTO {

    private Integer orderId;
    private Long userId;
    private Date orderDate;
    private String paymentMethod;
    private String orderStatus;
    private Date deliverDate;
    private Date pickupDate;
    private String deliverAddress;
    private String recipientName;
    private String recipientPhone;
    private Date paymentTime;

}
