/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

/**
 * HmacUtils
 * XOP接口签名工具类
 *
 * @author LiuJibin
 */
public class HmacUtils {

    /**
     * xop的签名逻辑，签名过程包括
     * 1、需传入appId，secret，Map参数，和body；若Map中有nonce会被替换为固定格式值，body若有值也会进行签名
     * 2、签名过程：map参数进行排序后+body（有值时会签名）+secret 以上字符串进行md5得到签名值
     *
     * @param appId 应用凭证Id
     * @param secret 应用凭证秘钥
     * @param urlParam url参数
     * @param requestBodyStr 请求体字符串
     * @return 返回签名结果，包括：时间戳，签名值，随机nonce
     * @throws RuntimeException SHA1签名异常
     */
    public static HmacResult sign(String appId, String secret, final Map<String, ?> urlParam, String requestBodyStr) throws RuntimeException {
        return sign(appId, secret, urlParam, requestBodyStr, HmacConst.KEY_SIGN, HmacUtils::getSignatureString);
    }

    protected static HmacResult sign(String appId, String secret, final Map<String, ?> urlParam, String requestBodyStr, String signHeader, SignatureFunction signatureFunction) throws RuntimeException {
        Map<String, Object> map = new HashMap<>(8);
        if (urlParam != null) {
            map.putAll(urlParam);
        }
        // get AccessTokenId, sign
        map.put(HmacConst.KEY_APP_ID, appId);
        // 去掉传递过来的sign
        XopHmacVersionEnum.clearSignFromMap(map);
        // 去掉传递过来的nonce，一律在此统一放入
        map.remove(HmacConst.KEY_NONCE);
        String nonce = getNonce();
        map.put(HmacConst.KEY_NONCE, nonce);
        Long timeStamp = System.currentTimeMillis() / 1000;
        map.put(HmacConst.KEY_TIMESTAMP, timeStamp);
        String signStr = signatureFunction.apply(map, secret, requestBodyStr);
        map.put(signHeader, signStr);
        return new HmacResult(timeStamp, signStr, nonce);
    }

    /**
     * 生成请求参数中Nonce
     * @return nonce 字符串
     */
    public static String getNonce() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将map转换为字符串输出
     *
     * @param params 请求参数
     * @return 参数拼接后的字符串
     */
    public static String generateQueryString(Map<String, Object> params) {
        List<String> tempParams = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String str = entry.getKey() + "="
                + (entry.getValue() instanceof String ? entry.getValue() : new Gson().toJson(entry.getValue()));
            tempParams.add(str);
        }
        return String.join("&", tempParams);
    }

    /**
     * 签名值的实现逻辑
     *
     * @param urlParams   参数
     * @param secret      秘钥
     * @param requestBodyStr 请求体字符串
     * @return 返回签名值
     * @throws RuntimeException SHA1计算错误
     */
    protected static String getSignatureString(final Map<String, ?> urlParams, String secret, String requestBodyStr)
        throws RuntimeException {
        Map<String, Object> signParams = new HashMap<>(urlParams);
        if (requestBodyStr != null && !Objects.equals("", requestBodyStr)) {
            signParams.put(HmacConst.REQUEST_BODY, requestBodyStr);
        }
        TreeMap<String, Object> sortParams = new TreeMap<>(signParams);
        String sortParamStr = generateQueryString(sortParams);
        String keyListStr = sortParamStr + "&secret=" + secret;
        String base64Str = base64Str(keyListStr);
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            byte[] bytes = sha1.digest(base64Str.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Invalid sign");
        }
    }

    protected static String base64Str(String str) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = encoder.encode(str.getBytes(StandardCharsets.UTF_8.name()));
            return new String(bytes, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("unsupported");
        }
    }

}
