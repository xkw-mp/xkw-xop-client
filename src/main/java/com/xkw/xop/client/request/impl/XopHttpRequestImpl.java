/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.request.impl;

import com.xkw.xop.client.request.XopHttpRequest;
import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopRequest
 * @author LiuJibin
 */
public class XopHttpRequestImpl extends XopHttpRequestBase
    implements XopHttpRequest {

    @Override
    public XopHttpRequest httpMethod(HttpMethod httpMethod) {
        setHttpMethod(httpMethod);
        return this;
    }

    @Override
    public XopHttpRequest uri(String uri) {
        setUri(uri);
        return this;
    }

    @Override
    public XopHttpRequest customHeaders(Map<String, String> customHeaders) {
        setCustomHeaders(customHeaders);
        return this;
    }

    @Override
    public XopHttpRequest queryParams(Map<String, Object> queryParams) {
        setQueryParams(queryParams);
        return this;
    }

    @Override
    public XopHttpRequest body(Object body) {
        setBody(body);
        return this;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return super.getHttpMethod();
    }

    @Override
    public String getUri() {
        return super.getUri();
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return super.getCustomHeaders();
    }

    @Override
    public Map<String, Object> getQueryParams() {
        return super.getQueryParams();
    }

    @Override
    public Object getBody() {
        return super.getBody();
    }
}
