package com.willy.malltest.dto;


import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberShowOrdersDTO {

    private Integer orderId;
    private User user;
    private Date orderDate;
    private String paymentMethod;
    private String orderStatus;
    private Date deliverDate;
    private Date pickupDate;
    private String deliverAddress;
    private String recipientName;
    private String recipientPhone;
    private Date paymentTime;
    private List<OrderDetailDTO> orderDetails = new ArrayList<>();
//    private int orderPrice;
//    private String productSpec;
//    private int quantity;
//    private int price;
//    private String color;
//    private String productName;
}
