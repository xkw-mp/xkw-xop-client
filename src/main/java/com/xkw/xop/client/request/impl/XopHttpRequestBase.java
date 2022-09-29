/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.request.impl;

import kong.unirest.HttpMethod;

import java.util.Map;

import static com.xkw.xop.client.utils.Constants.GSON;

/**
 * XopHttpRequestBase
 * description
 *
 * @author LiuJibin
 */
public class XopHttpRequestBase {

    private HttpMethod httpMethod;
    private String uri;
    private Map<String, String> customHeaders;
    private Map<String, Object> queryParams;
    private Object body;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getBodyString() {
        String bodyString = null;
        if (body instanceof String) {
            bodyString = (String)body;
        } else if (body != null) {
            bodyString = GSON.toJson(body);
        }
        return bodyString;
    }

}
