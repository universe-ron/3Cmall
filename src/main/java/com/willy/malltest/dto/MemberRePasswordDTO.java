package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberRePasswordDTO {
    private Long userID;
    private String userName;
    private String email;
    private String password;
}
