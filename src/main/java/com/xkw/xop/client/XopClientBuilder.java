/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.xkw.xop.client.utils.Constants;
import com.xkw.xop.client.utils.Utils;
import kong.unirest.Config;
import kong.unirest.Proxy;
import kong.unirest.apache.ApacheClient;

import static com.xkw.xop.client.utils.Constants.MAX_CONNECTION_PER_ROUTE;
import static com.xkw.xop.client.utils.Constants.MAX_CONNECTION_POOL_SIZE;
import static com.xkw.xop.client.utils.Constants.TIMEOUT;

/**
 * XopClientBuilder
 * @author LiuJibin
 */
public class XopClientBuilder {

    /**
     * XOP网关Uri，无需设置
     */
    private String gatewayHost;
    /**
     * 应用Id
     */
    private String appId;
    /**
     * 应用秘钥
     */
    private String secret;
    /**
     * 超时时长，默认：10秒
     */
    private Integer timeout;
    /**
     * 代理设置
     */
    private Proxy proxy;
    /**
     * 连接池大小
     */
    private int maxPoolSize;
    private int maxConnectionPerRoute;
    private Integer connectionValidatePeriod;
    private Config config;

    public XopClientBuilder gatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
        return this;
    }

    public XopClientBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }
    public XopClientBuilder secret(String secret) {
        this.secret = secret;
        return this;
    }

    public XopClientBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
    public XopClientBuilder proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }
    public XopClientBuilder maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }
    public XopClientBuilder maxConnectionPerRoute(int maxConnectionPerRoute) {
        this.maxConnectionPerRoute = maxConnectionPerRoute;
        return this;
    }

    public XopClientBuilder connectionValidatePeriod(Integer connectionValidatePeriod) {
        this.connectionValidatePeriod = connectionValidatePeriod;
        return this;
    }

    private void build() throws RuntimeException {
        if (Utils.isEmpty(gatewayHost)) {
            gatewayHost = Constants.XOP_HOST_URL;
        }
        if (Utils.isEmpty(appId)) {
            throw new RuntimeException("App Id must be set!");
        }
        if (Utils.isEmpty(secret)) {
            throw new RuntimeException("App secret must be set!");
        }
        config = new Config();
        if (timeout == null) {
            timeout = TIMEOUT;
        }

        config.socketTimeout(timeout * 1000)
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
    }

    public XopHttpClient buildHttpClient() {
        this.build();
        return new XopHttpClient(gatewayHost, appId, secret, config);
    }

}