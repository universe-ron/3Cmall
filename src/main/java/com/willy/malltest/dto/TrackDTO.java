package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackDTO {

    private Integer trackID;
    private Long userID; // 將使用者ID添加到DTO中
    private String specID; // 將規格ID添加到DTO中
}
