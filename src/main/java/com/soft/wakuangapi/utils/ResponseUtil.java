package com.soft.wakuangapi.utils;

import lombok.Data;

@Data
public class ResponseUtil {
    private int code;
    private String msg;
    private Object data;

    public ResponseUtil(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseUtil() {
    }
}
