package com.wch.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class APIResponse<T> {

    private int code;
    private String msg;
    private T data;

    public APIResponse() {}

    public APIResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static APIResponse success() {
        return new APIResponse(200, "操作成功！");
    }

    public static APIResponse error(String msg) {
        return new APIResponse(400, msg);
    }

}
