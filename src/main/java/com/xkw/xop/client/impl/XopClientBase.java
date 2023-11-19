/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.impl;

import com.xkw.xop.client.hmac.HmacUtilsV2;
import com.xkw.xop.client.hmac.XopHmacVersionEnum;
import com.xkw.xop.client.request.impl.XopHttpRequestBase;
import com.xkw.xop.client.response.XopHttpResponse;
import com.xkw.xop.client.response.impl.XopHttpResponseImpl;
import com.xkw.xop.client.utils.XopClientUtils;
import com.xkw.xop.client.hmac.HmacConst;
import com.xkw.xop.client.hmac.HmacResult;
import com.xkw.xop.client.hmac.HmacUtils;
import kong.unirest.Config;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.xkw.xop.client.utils.Constants.CONTENT_TYPE_JSON;

/**
 * XopClientBase
 * description
 *
 * @author LiuJibin
 */
public class XopClientBase {

    protected final String gatewayHost;
    protected final String appId;
    protected final String secret;

    protected final UnirestInstance client;
    protected final XopHmacVersionEnum hmacVersionEnum;

    public XopClientBase(String gatewayHost, String appId, String secret, Config config, XopHmacVersionEnum hmacVersionEnum) {
        this.gatewayHost = gatewayHost;
        this.appId = appId;
        this.secret = secret;
        if (config == null) {
            config = new Config();
        }
        this.hmacVersionEnum = hmacVersionEnum;
        client = new UnirestInstance(config);
    }

    protected XopHttpResponse<String> getHttpResponse(XopHttpRequestBase request, Map<String, String> headerMap
        , String bodyString, Map<String, String> customHeaderMap) {
        // valid request
        if (request.getHttpMethod() == null) {
            throw new IllegalArgumentException("http method can NOT be null");
        }
        if (XopClientUtils.isEmpty(request.getUri())) {
            throw new IllegalArgumentException("http uri can NOT be null");
        }
        HttpMethod method = request.getHttpMethod();
        String fullUri = gatewayHost + request.getUri();
        HttpResponse<String> response;

        String requestId = XopClientUtils.getRequestId();
        headerMap.put(HmacConst.KEY_REQUEST_ID, requestId);
        // 校验CustomerHeader
        if (customHeaderMap != null && customHeaderMap.size() > 0) {
            Map<String, String> wholeHeaderMap = new HashMap<>(headerMap);
            wholeHeaderMap.putAll(customHeaderMap);
            if (headerMap.size() + customHeaderMap.size() != wholeHeaderMap.size()) {
                // 输出相同Header
                Set<String> dupHeaderSet = new HashSet<>(customHeaderMap.keySet());
                dupHeaderSet.retainAll(headerMap.keySet());
                throw new IllegalArgumentException("自定义Header中包含XOP客户端所传Key： " + String.join(",", dupHeaderSet));
            }
            headerMap.putAll(customHeaderMap);
        }
        if (method == HttpMethod.GET) {
            response = client.get(fullUri)
                .headers(headerMap)
                .queryString(request.getQueryParams())
                .asString();
        } else {
            HttpRequestWithBody req = client.post(fullUri).headers(headerMap).queryString(request.getQueryParams());
            if (XopClientUtils.isEmpty(bodyString)) {
                response = req.asString();
            } else {
                //body传Object对象会使用 json 序列化的重载方法
                response = req.body(bodyString).asString();
            }
        }
        return new XopHttpResponseImpl<>(response, requestId);
    }

    protected HmacResult getHmacResult(String uri, Map<String, Object> queryParams, String bodyString) {
        Map<String, Object> map = new HashMap<>(8);
        if (queryParams != null) {
            map.putAll(queryParams);
        }
        map.put(HmacConst.KEY_URL, uri);
        if (XopHmacVersionEnum.V2 == this.hmacVersionEnum) {
            return HmacUtilsV2.sign(appId, secret, map, bodyString);
        }
        return HmacUtils.sign(appId, secret, map, bodyString);
    }

    protected Map<String, String> getHeaderMap(HmacResult result) {
        Map<String, String> headerMap = new HashMap<>(8);
        headerMap.put(HmacConst.KEY_APP_ID, appId);
        headerMap.put(HmacConst.KEY_TIMESTAMP, result.getTimeStamp().toString());
        headerMap.put(hmacVersionEnum.getSignHeader(), result.getSign());
        headerMap.put(HmacConst.KEY_NONCE, result.getNonce());
        headerMap.put("Content-Type", CONTENT_TYPE_JSON);
        return headerMap;
    }

}
