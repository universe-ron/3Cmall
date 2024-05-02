package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_name")
    private String productName;

//    @Column(name = "CategoryID", nullable = false)
//    private String categoryID;

    @Column(name = "price")
    private int price;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "chip")
    private String chip;

    @Column(name = "wifi")
    private String wifi;

    @Column(name = "size")
    private String size;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "memory")
    private String memory;

    @Column(name = "product_disk")
    private String productDisk;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "setup_date")
    private Date setupDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //延遲加載
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany( mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSpec> productSpecs = new ArrayList<>();

    @Column(name = "sales_status")
    private Integer salesStatus;
}
