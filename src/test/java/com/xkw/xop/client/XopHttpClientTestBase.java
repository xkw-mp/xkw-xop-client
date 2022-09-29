/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * XopHttpClientTestBase
 * description
 *
 * @author LiuJibin
 */
public class XopHttpClientTestBase {

    // 凭证
    private static final String APP_ID = "";
    private static final String SECRET = "";
    // URL
    protected static final String GET_URL = "/xopqbm/areas";
    protected static final String POST_URL = "/xopqbm/questions";

    protected static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    protected static XopHttpClient CLIENT = new XopClientBuilder()
        .appId(APP_ID)
        .secret(SECRET)
        .timeout(10).maxConnectionPerRoute(10)
        .buildHttpClient();

}
