package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;  //PRIMARY KEY identity(1,1),

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;  //foreign key,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "order_status")
    private String orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE")// 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "deliver_date")
    private Date deliverDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE")// 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "pickup_date")
    private Date pickupDate;

    @Column(name = "deliver_address")
    private String deliverAddress;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE")// 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "payment_time")
    private Date paymentTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = CascadeType.ALL )
    @JsonIgnore
    private List<OrdersDetail> ordersDetails = new ArrayList<>();

    //test
    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    private List<CustomerFeedback> customerFeedback;

}
