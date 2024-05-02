package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrackDeDTO {
    private Integer trackID;
    private Long userID;
    private List<String> specID;
}
