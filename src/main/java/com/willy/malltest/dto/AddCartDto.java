package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartDto {
    private Long userId;
    private String specId;
    private int quantity;
    public AddCartDto(Long userId, String specId, int quantity){
        this.userId = userId;
        this.specId = specId;
        this.quantity = quantity;
    }
}
