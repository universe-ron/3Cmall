package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "customer_feedback")

public class CustomerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackID;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "customer_feedback_status")
    private String customerFeedbackStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "feedback_date")
    private Date feedbackDate;

    @PrePersist // 在物件轉換到 Persistent 狀態以前，做這個 function
    public void onCreate() {
        if (feedbackDate == null) {
            feedbackDate = new Date();
        }
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "orders_detail_id")
    @JsonIgnore
    private OrdersDetail ordersDetails;
}