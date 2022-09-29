/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.impl;

import com.xkw.xop.client.XopHttpClient;
import com.xkw.xop.client.request.XopHttpRequest;
import com.xkw.xop.client.request.impl.XopHttpRequestImpl;
import com.xkw.xop.client.response.XopHttpResponse;
import com.xkw.xop.client.hmac.HmacResult;
import kong.unirest.Config;
import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopHttpClient
 * 学科网开放平台XOP，HTTP请求客户端
 *
 * @author LiuJibin
 */
public class XopHttpClientImpl extends XopClientBase
    implements XopHttpClient {

    public XopHttpClientImpl(String gatewayHost, String appId, String secret, Config config) {
        super(gatewayHost, appId, secret, config);
    }

    @Override
    public XopHttpResponse<String> get(String uri) {
        return get(uri, null);
    }

    @Override
    public XopHttpResponse<String> get(String uri, Map<String, Object> queryParams) {
        return sendRequest(HttpMethod.GET, uri, queryParams, null);
    }

    @Override
    public XopHttpResponse<String> post(String uri, Map<String, Object> queryParams, Object body) {
        return sendRequest(HttpMethod.POST, uri, queryParams, body);
    }

    @Override
    public XopHttpRequest getRequest() {
        XopHttpRequestImpl request = new XopHttpRequestImpl();
        return request.httpMethod(HttpMethod.GET);
    }

    @Override
    public XopHttpRequest postRequest() {
        XopHttpRequestImpl request = new XopHttpRequestImpl();
        return request.httpMethod(HttpMethod.POST);
    }

    @Override
    public XopHttpResponse<String> sendRequest(XopHttpRequest request) {
        return sendRequest(request.getHttpMethod(), request.getUri(), request.getQueryParams(), request.getBody()
            , request.getCustomHeaders());
    }
    private XopHttpResponse<String> sendRequest(HttpMethod method, String uri, Map<String, Object> queryParams
        , Object body) {
        return sendRequest(method, uri, queryParams, body, null);
    }

    private XopHttpResponse<String> sendRequest(HttpMethod method, String uri, Map<String, Object> queryParams
        , Object body, Map<String, String> customHeaderMap) {
        XopHttpRequestImpl request = new XopHttpRequestImpl();
        request.httpMethod(method)
            .uri(uri)
            .queryParams(queryParams)
            .body(body);
        String bodyString = request.getBodyString();
        HmacResult result = getHmacResult(uri, queryParams, bodyString);
        Map<String, String> headerMap = getHeaderMap(result);
        return getHttpResponse(request, headerMap, bodyString, customHeaderMap);
    }

}