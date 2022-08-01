/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.xkw.xop.client.response.XopHttpResponse;
import com.xkw.xop.client.utils.Utils;
import com.xkw.xop.client.hmac.HmacConst;
import com.xkw.xop.client.hmac.HmacResult;
import com.xkw.xop.client.hmac.HmacUtils;
import kong.unirest.Config;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;

import java.util.HashMap;
import java.util.Map;

import static com.xkw.xop.client.utils.Constants.CONTENT_TYPE_JSON;
import static com.xkw.xop.client.utils.Constants.GSON;

/**
 * XopHttpClient
 * 学科网开放平台XOP，HTTP请求客户端
 * @author LiuJibin
 */
@SuppressWarnings("unused")
public class XopHttpClient {

    protected final String gatewayHost;
    protected final String appId;
    protected final String secret;

    protected final UnirestInstance client;

    XopHttpClient(String gatewayHost, String appId, String secret, Config config) {
        this.gatewayHost = gatewayHost;
        this.appId = appId;
        this.secret = secret;
        if (config == null) {
            config = new Config();
        }
        client = new UnirestInstance(config);
    }

    /**
     * GET无参请求
     * @param uri uri
     * @return @see XopHttpResponse
     */
    public XopHttpResponse<String> get(String uri) {
        return get(uri, null);
    }

    /**
     * GET有参请求
     * @param uri uri
     * @param queryParams query参数
     * @return @see XopHttpResponse
     */
    public XopHttpResponse<String> get(String uri, Map<String, Object> queryParams) {
        return sendRequest(HttpMethod.GET, uri, queryParams, null);
    }

    /**
     * POST请求
     * @param uri uri
     * @param queryParams query参数
     * @param body 请求体
     * @return @see XopHttpResponse
     */
    public XopHttpResponse<String> post(String uri, Map<String, Object> queryParams, Object body) {
        return sendRequest(HttpMethod.POST, uri, queryParams, body);
    }

    public XopHttpResponse<String> sendRequest(HttpMethod method, String uri
        , Map<String, Object> queryParams, Object body) {
        String bodyString = null;
        if (body instanceof String) {
            bodyString = (String)body;
        }
        else if (body != null) {
            bodyString = GSON.toJson(body);
        }
        HmacResult result = getHmacResult(uri, queryParams, bodyString);
        Map<String, String> headerMap = getHeaderMap(result);
        return getHttpResponse(method, uri, headerMap, queryParams, bodyString);
    }

    protected HmacResult getHmacResult(String uri, Map<String, Object> queryParams, String bodyString) {
        Map<String, Object> map = new HashMap<>(8);
        if (queryParams != null) {
            map.putAll(queryParams);
        }
        map.put(HmacConst.KEY_URL, uri);
        return HmacUtils.sign(appId, secret, map, bodyString);
    }

    protected Map<String, String> getHeaderMap(HmacResult result) {
        Map<String, String> headerMap = new HashMap<>(8);
        headerMap.put(HmacConst.KEY_APP_ID, appId);
        headerMap.put(HmacConst.KEY_TIMESTAMP, result.getTimeStamp().toString());
        headerMap.put(HmacConst.KEY_SIGN, result.getSign());
        headerMap.put(HmacConst.KEY_NONCE, result.getNonce());
        headerMap.put("Content-Type", CONTENT_TYPE_JSON);
        return headerMap;
    }

    protected XopHttpResponse<String> getHttpResponse(HttpMethod method, String uri, Map<String, String> headerMap, Map<String, Object> queryParams,
        String bodyString) {
        String fullUri = gatewayHost + uri;
        HttpResponse<String> response;

        String requestId = Utils.getRequestId();
        headerMap.put(HmacConst.KEY_REQUEST_ID, requestId);
        if (method == HttpMethod.GET) {
            response = client.get(fullUri)
                .headers(headerMap)
                .queryString(queryParams)
                .asString();
        } else {
            HttpRequestWithBody req = client.post(fullUri).headers(headerMap).queryString(queryParams);
            if (Utils.isEmpty(bodyString)) {
                response = req.asString();
            } else {
                //body传Object对象会使用 json 序列化的重载方法
                response = req.body(bodyString).asString();
            }
        }
        return new XopHttpResponse<>(response, requestId);
    }


}
