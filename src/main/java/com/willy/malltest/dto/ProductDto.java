package com.willy.malltest.dto;


import com.willy.malltest.model.ProductSpec;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    private String productId;
    private int price;
    private Integer photoId;

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    private String productDescription;

    public List<String> getSpecIds() {
        return specIds;
    }

    public void setSpecIds(List<String> specIds) {
        this.specIds = specIds;
    }

    private List<String> specIds;



}
