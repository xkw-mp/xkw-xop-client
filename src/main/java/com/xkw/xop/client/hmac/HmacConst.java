/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

/**
 * HmacConst
 * Hmac常量
 *
 * @author Wendy
 * @date 2021/07/05
 */
public class HmacConst {

    /**
     * 时间戳
     */
    public static final String KEY_TIMESTAMP = "Xop-Timestamp";
    /**
     * 签名
     */
    public static final String KEY_SIGN = "Xop-Sign";
    /**
     * 防止重放攻击的随机串
     */
    public static final String KEY_NONCE = "Xop-Nonce";
    /**
     * 的accessId
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

}