package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "return_details")
public class ReturnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_detail_id")
    private Integer returnDetailId;

    @OneToOne
    @JoinColumn(name = "orders_detail_id")
    @JsonIgnore
    private OrdersDetail ordersDetail;

    @Column(name = "return_reason")
    private String returnReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_id")
    private Returns returns;
}
