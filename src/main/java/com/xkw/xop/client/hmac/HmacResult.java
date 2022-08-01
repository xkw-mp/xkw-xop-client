/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

/**
 * HmacResult
 * Hmac生成签名结果
 *
 * @author Wendy
 */
public class HmacResult {
    private Long timeStamp;
    private String sign;
    private String nonce;

    public HmacResult(Long timeStamp, String sign, String nonce) {
        this.timeStamp = timeStamp;
        this.sign = sign;
        this.nonce = nonce;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}