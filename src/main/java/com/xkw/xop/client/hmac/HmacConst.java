/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

/**
 * HmacConst
 * XOP-HMAC算法所用常量
 *
 * @author Wendy
 */
@SuppressWarnings("unused")
public class HmacConst {
    /**
     * 请求发起时的时间戳
     */
    public static final String KEY_TIMESTAMP = "Xop-Timestamp";
    /**
     * HMAC签名
     */
    public static final String KEY_SIGN = "Xop-Sign";
    /**
     * 防止重放攻击的随机串
     */
    public static final String KEY_NONCE = "Xop-Nonce";
    /**
     * 请求所用的应用Id
     */
    public static final String KEY_APP_ID = "Xop-App-Id";
    /**
     * 请求url，不含query
     */
    public static final String KEY_URL  = "xop_url";
    /**
     * POST请求的Body
     */
    public static final String REQUEST_BODY  = "xop_body";
    /**
     * 请求跟踪Id
     */
    public static final String KEY_REQUEST_ID = "X-Request-Id";
}
