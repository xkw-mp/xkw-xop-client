/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.utils;

import kong.unirest.HttpResponse;

import java.util.Optional;
import java.util.UUID;

/**
 * Utils
 * 常用工具
 * @author LiuJibin
 */
public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 获取状态码
     * @param response res
     * @return status
     */
    public static int getStatus(HttpResponse<String> response) {
        return Optional.ofNullable(response).map(HttpResponse::getStatus).orElse(524);
    }

    /**
     * 判断状态码是否成功，非4xx、非5xx
     * @param status 状态码
     * @return true，请求成功
     */
    public static boolean statusSuccess(int status) {
        return !(status >= 400 && status < 600);
    }

    /**
     * 生成RequestId，用于标识每个请求
     * RequestId只是一串随机字符串，本身没有任何意义
     * @return requestId
     */
    public static String getRequestId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
