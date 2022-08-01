/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client.utils;

import com.google.gson.Gson;

/**
 * Constants
 * @author LiuJibin
 */
public class Constants {

    public static final String XOP_HOST_URL = "https://openapi.xkw.com";

    public static final Gson GSON = new Gson();

    public static final Integer TIMEOUT = 10;
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 默认最大连接池数
     */
    public static final Integer MAX_CONNECTION_POOL_SIZE = 100;
    /**
     * 单个路由，默认最大连接数
     */
    public static final Integer MAX_CONNECTION_PER_ROUTE = 100;

}
