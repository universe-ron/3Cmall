package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    public User() {
        super();
    }
    public User(Long userId){
        this.userId=userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user")
    private List<ThirdParty> thirdParty = new ArrayList<>();

    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "register_date")
    private Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "deliver_address")
    private String deliverAddress;
    @Column(name = "phone")
    private String phone;
    @Column(name = "age")
    private Integer age;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "authentication")
    private Integer authentication;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orders> orders = new ArrayList<>();


    //test
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Track> track;

    //test
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CustomerFeedback> customerFeedback;

}
