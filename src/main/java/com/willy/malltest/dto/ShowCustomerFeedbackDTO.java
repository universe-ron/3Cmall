package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShowCustomerFeedbackDTO {
        private Integer feedbackID;
        private Long userID;
        private Integer orderID;
        private String Type;
        private String Description;
        private Date feedbackDate;
        private String CustomerFeedbackStatus;
        private List<String> productNames; // 改成列表来接收产品名称
        private List<Integer> prices; // 改成列表来接收价格
        private List<Integer> quantities;

}

