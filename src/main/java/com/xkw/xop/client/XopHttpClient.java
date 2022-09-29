/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.xkw.xop.client.request.XopHttpRequest;
import com.xkw.xop.client.response.XopHttpResponse;

import java.util.Map;

/**
 * XopHttpClient
 * 学科网开放平台XOP，HTTP请求客户端
 *
 * @author LiuJibin
 */
public interface XopHttpClient {

    /**
     * GET无参请求
     * @param uri uri
     * @return @see XopHttpResponse
     */
    XopHttpResponse<String> get(String uri);

    /**
     * GET有参请求
     * @param uri uri
     * @param queryParams query参数
     * @return @see XopHttpResponse
     */
    XopHttpResponse<String> get(String uri, Map<String, Object> queryParams);

    /**
     * POST请求
     * @param uri uri
     * @param queryParams query参数
     * @param body 请求体
     * @return @see XopHttpResponse
     */
    XopHttpResponse<String> post(String uri, Map<String, Object> queryParams, Object body);

    /**
     * 生成XopHttpRequest，GET方法
     * @return XopHttpRequest
     */
    XopHttpRequest getRequest();

    /**
     * 生成XopHttpRequest，POST方法
     * @return XopHttpRequest
     */
    XopHttpRequest postRequest();

    /**
     * 发送请求，结果解析为String
     * @param request XopHttpRequest
     * @return @see XopHttpResponse
     */
    XopHttpResponse<String> sendRequest(XopHttpRequest request);

}
