/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.request;

import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopHttpRequest
 * Xop Http 请求
 *
 * @author LiuJibin
 */
public interface XopHttpRequest {

    /**
     * 请求方法，现在支持GET/POST
     * @param httpMethod get/post
     * @return XopHttpRequest
     */
    XopHttpRequest httpMethod(HttpMethod httpMethod);

    /**
     * uri
     * @param uri uri
     * @return XopHttpRequest
     */
    XopHttpRequest uri(String uri);

    /**
     * 自定义Header
     * @param customHeaders 自定义Header Map
     * @return XopHttpRequest
     */
    XopHttpRequest customHeaders(Map<String, String> customHeaders);

    /**
     * query请求参数
     * @param queryParams query参数
     * @return XopHttpRequest
     */
    XopHttpRequest queryParams(Map<String, Object> queryParams);

    /**
     * 请求体，仅在POST方法中使用
     * @param body 请求体，Object
     * @return XopHttpRequest
     */
    XopHttpRequest body(Object body);

    /**
     * 以下为Get方法
     */
    HttpMethod getHttpMethod();

    String getUri();

    Map<String, String> getCustomHeaders();

    Map<String, Object> getQueryParams();

    Object getBody();

}
