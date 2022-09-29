/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.response;

/**
 * XopResponse
 * XOP接口返回内容格式（Body中返回的内容）
 *
 * @author KQS
 */
public class XopResponse<T> {

    /**
     * 接口调用成功的成功码；具体请参考接口文档
     */
    public static Integer SUCCESS = 2000000;

    /**
     * 错误码，用于标识具体错误
     */
    private Integer code;
    /**
     * 错误说明
     */
    private String msg;
    /**
     * 具体数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
