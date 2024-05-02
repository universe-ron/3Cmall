package com.willy.malltest.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrackShowDTO {
    private Integer trackID;
    private Long userID;
    private String specID;
    private String productId;  // 新增 productId 屬性
    private Integer productPrice;
    private String photoFile;
    private String productName;
    private String productDescription;
    private List<String> specIds;  // 規格ID列表
}
