package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private String orderDetailId;
    private String productSpec;
    private String productName;
    private String color;
    private Integer quantity;
    private Integer price;
    private int orderPrice;
}
