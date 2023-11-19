/*
 * xkw.com Inc. Copyright (c) 2023 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

import java.util.Map;

/**
 * SignatureFunction
 * Hmac签名函数
 *
 * @author LiuJibin
 */
@FunctionalInterface
public interface SignatureFunction {

    /**
     * 计算签名值
     *
     * @param urlParams   参数
     * @param secret      秘钥
     * @param requestBodyStr 请求体字符串
     * @return 签名值字符串
     */
    String apply(final Map<String, ?> urlParams, String secret, String requestBodyStr);

}
