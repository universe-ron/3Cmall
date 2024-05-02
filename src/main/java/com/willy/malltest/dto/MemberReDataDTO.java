package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MemberReDataDTO {
    private Long userID;
    private String userName;
    private String email;
    private Date registerDate;
    private String userAddress;
    private String deliverAddress;
    private String phone;
}
