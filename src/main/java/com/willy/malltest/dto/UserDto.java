package com.willy.malltest.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Data
public class UserDto {

    private Long userId;
    private String userName;
    private String email;
    //private String password;
    private Date registerDate;
    private Date lastLoginTime;
    private String userAddress;
    private String deliverAddress;
    private String phone;
    private Integer age;
    private Integer sex;
    private Integer authentication;


}
