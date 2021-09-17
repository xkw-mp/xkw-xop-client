/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.google.gson.Gson;
import com.xkw.xop.client.hmac.HmacConst;
import com.xkw.xop.client.hmac.HmacResult;
import com.xkw.xop.client.hmac.HmacUtils;
import kong.unirest.Config;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;
import kong.unirest.Proxy;
import kong.unirest.UnirestInstance;
import kong.unirest.apache.ApacheClient;

import java.util.HashMap;
import java.util.Map;

/**
 * XopHttpClient
 * XOP（学科网开放平台）HTTP 请求客户端
 *
 * @author LiuJibin
 * @date 2021/07/01
 */
public class XopHttpClient {

    /**
     * XOP-Gateway地址
     */
    public static final String XOP_GATEWAY_HOST = "https://openapi.xkw.com";

    /**
     * 默认超时时间10秒钟
     */
    private static final Integer TIMEOUT_SECONDS = 10;

    /**
     * 请求结果格式
     */
    private static final String CONTENT_TYPE_JSON = "application/json";
    /**
     * 默认最大连接池数
     */
    private static final Integer MAX_CONNECTION_POOL_SIZE = 100;
    /**
     * 单路由默认最大连接数
     */
    private static final Integer MAX_CONNECTION_PER_ROUTE = 100;
    /**
     * XOP网关地址
     */
    private final String gatewayHost;
    private final String appId;
    private final String secret;

    private final UnirestInstance client;

    private static final Gson GSON = new Gson();


    public XopHttpClient(String gatewayHost, String appId, String secret, Config config) {
        this.gatewayHost = gatewayHost;
        this.appId = appId;
        this.secret = secret;
        if (config == null) {
            config = new Config();
        }

        client = new UnirestInstance(config);
    }

    /**
     * 带query参数的GET请求
     * 注：uri中不能包含query参数！
     * @param uri uri不包含host、query
     * @param queryParamMap query参数Map
     * @return Unirest HttpResponse
     */
    public HttpResponse<String> get(String uri, Map<String, Object> queryParamMap) {
        return sendRequest(HttpMethod.GET, uri, queryParamMap, null);
    }

    /**
     * POST请求
     * 注：uri中不能包含query参数！
     * @param uri uri不包含host、query
     * @param queryParamMap query参数Map
     * @param body body参数，没有请传null
     * @return Unirest HttpResponse
     */
    public HttpResponse<String> post(String uri, Map<String, Object> queryParamMap, Object body) {
        String bodyString = GSON.toJson(body);
        return sendRequest(HttpMethod.POST, uri, queryParamMap, bodyString);
    }

    private HttpResponse<String> sendRequest(HttpMethod method, String uri
        , Map<String, Object> parameters, String bodyString) {
        Map<String, Object> map = new HashMap<>(8);
        if (parameters != null) {
            map.putAll(parameters);
        }
        map.put(HmacConst.KEY_URL, uri);
        HmacResult result = HmacUtils.sign(appId, secret, map, bodyString);
        String fullUri = gatewayHost + uri;
        Map<String, String> headerMap = new HashMap<>(8);
        headerMap.put(HmacConst.KEY_APP_ID, appId);
        headerMap.put(HmacConst.KEY_TIMESTAMP, result.getTimeStamp().toString());
        headerMap.put(HmacConst.KEY_SIGN, result.getSign());
        headerMap.put(HmacConst.KEY_NONCE, result.getNonce());
        headerMap.put("Content-Type", CONTENT_TYPE_JSON);
        HttpResponse<String> response;
        if (method == HttpMethod.GET) {
            response = client.get(fullUri)
                .headers(headerMap)
                .queryString(parameters)
                .asString();
        } else {
            response = client.post(fullUri)
                .headers(headerMap)
                .queryString(parameters)
                .body(bodyString)
                .asString();
        }
        return response;
    }

    public UnirestInstance getClient() {
        return client;
    }

    public static class Builder {
        private String gatewayHost;
        private String appId;
        private String secret;
        private Integer timeout;
        private Proxy proxy;
        private int maxPoolSize;
        private int maxConnectionPerRoute;
        private Integer connectionValidatePeriod;

        public Builder gatewayHost(String gatewayHost) {
            this.gatewayHost = gatewayHost;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }
        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }
        public Builder proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }
        public Builder maxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }
        public Builder maxConnectionPerRoute(int maxConnectionPerRoute) {
            this.maxConnectionPerRoute = maxConnectionPerRoute;
            return this;
        }

        public Builder connectionValidatePeriod(Integer connectionValidatePeriod) {
            this.connectionValidatePeriod = connectionValidatePeriod;
            return this;
        }

        public XopHttpClient build() throws RuntimeException {
            if (isEmpty(gatewayHost)) {
                gatewayHost = XOP_GATEWAY_HOST;
            }
            if (isEmpty(appId)) {
                throw new RuntimeException("XOP App-Id must be set!");
            }
            if (isEmpty(secret)) {
                throw new RuntimeException("XOP App-secret must be set!");
            }
            Config config = new Config();
            if (timeout == null) {
                timeout = TIMEOUT_SECONDS;
            }

            config
                .socketTimeout(timeout * 1000)
                .connectTimeout(timeout * 1000);
            if (maxPoolSize <= 0) {
                maxPoolSize = MAX_CONNECTION_POOL_SIZE;
            }
            if (maxConnectionPerRoute <= 0) {
                maxConnectionPerRoute = MAX_CONNECTION_PER_ROUTE;
            }
            config.concurrency(maxPoolSize, maxConnectionPerRoute);
            config.proxy(proxy);
            if (connectionValidatePeriod != null) {
                config.httpClient(conf -> {
                    ApacheClient cli = new ApacheClient(conf);
                    cli.getManager().setValidateAfterInactivity(connectionValidatePeriod);
                    return cli;
                });
            }

            return new XopHttpClient(gatewayHost, appId, secret, config);
        }

    }

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
