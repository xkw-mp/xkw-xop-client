/*
 * xkw.com Inc. Copyright (c) 2023 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * HmacUtilsV2
 * XOP接口签名工具类，使用xop-hmac v2算法
 *
 * @author LiuJibin
 */
public class HmacUtilsV2 extends HmacUtils {

    /**
     * xop的签名逻辑V2，签名过程包括
     * 1、需传入appId，secret，Map参数，和body；若Map中有nonce会被替换为固定格式值，body若有值也会进行签名
     * 2、签名过程：map参数进行排序后+body（有值时会签名）以上字符串，使用hmac-sha256（secret为盐）得到签名值
     *
     * @param appId 应用凭证Id
     * @param secret 应用凭证秘钥
     * @param urlParam url参数
     * @param requestBodyStr 请求体字符串
     * @return 返回签名结果，包括：时间戳，签名值，随机nonce
     * @throws RuntimeException SHA1签名异常
     */
    public static HmacResult sign(String appId, String secret, final Map<String, ?> urlParam, String requestBodyStr) throws RuntimeException {
        return sign(appId, secret, urlParam, requestBodyStr, HmacConst.KEY_SIGN_V2, HmacUtilsV2::getSignatureString);
    }

    /**
     * 签名值的实现逻辑
     *
     * @param urlParams   参数
     * @param secret      秘钥
     * @param requestBodyStr 请求体字符串
     * @return 返回签名值
     * @throws RuntimeException SHA256计算错误
     */
    protected static String getSignatureString(final Map<String, ?> urlParams, String secret, String requestBodyStr)
        throws RuntimeException {
        Map<String, Object> signParams = new HashMap<>(urlParams);
        if (requestBodyStr != null && !Objects.equals("", requestBodyStr)) {
            signParams.put(HmacConst.REQUEST_BODY, requestBodyStr);
        }
        TreeMap<String, Object> sortParams = new TreeMap<>(signParams);
        String keyListStr = generateQueryString(sortParams);
        String base64Str = base64Str(keyListStr);
        try {
            byte[] key = secret.getBytes(StandardCharsets.UTF_8);
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            hmacSha256.init(new SecretKeySpec(key, 0, key.length, ""));
            byte[] bytes = hmacSha256.doFinal(base64Str.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Invalid sign");
        }
    }

}
