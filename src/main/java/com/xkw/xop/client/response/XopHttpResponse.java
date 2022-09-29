/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.response;

import kong.unirest.HttpResponse;

/**
 * XopHttpResponse
 * description
 *
 * @author LiuJibin
 */
public interface XopHttpResponse<T> extends HttpResponse<T> {

    /**
     * 获取本次请求的Id，请求Id用于标识每次请求，
     * 主要用于调试
     * @return request-id
     */
    String getRequestId();

}
