package com.willy.malltest.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    private int code;
    private String message;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
