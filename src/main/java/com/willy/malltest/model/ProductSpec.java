package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@Entity
@Table(name = "product_spec")
@JsonIgnoreProperties({"product"})  // 忽略代理對象中的 product 屬性
public class ProductSpec {

    public ProductSpec() {
        super();
    }
    public ProductSpec(String specId) {
        super();
        this.specId = specId;
    }

    @Id
    @Column(name = "spec_id")
    private String specId;

    @Column(name = "color")
    private String color;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "deleted")
    private boolean deleted;


    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<CartItems> cartItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec",cascade = CascadeType.ALL)
    private List<ProductPhoto> productPhotos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<Track> tracks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<OrdersDetail> ordersDetails = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

}