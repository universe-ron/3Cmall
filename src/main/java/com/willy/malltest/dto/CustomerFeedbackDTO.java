package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerFeedbackDTO {
    private Integer feedbackID;
    private Long userID;
    private Integer orderID;
    private String Type;
    private String Description;
    private String UserName;
    private String BackendRecords;
    private Date feedbackDate;
    private String CustomerFeedbackStatus;
    private Integer ordersDetailId;
}
