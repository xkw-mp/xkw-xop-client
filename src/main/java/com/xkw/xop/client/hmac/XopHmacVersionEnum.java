/*
 * xkw.com Inc. Copyright (c) 2023 All Rights Reserved.
 */
package com.xkw.xop.client.hmac;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * XopHmacVersionEnum
 * xop-hmac版本枚举值
 *
 * @author LiuJibin
 */
public enum XopHmacVersionEnum {

    /**
     * 第一版
     */
    V1(1, HmacConst.KEY_SIGN),
    /**
     * 第二版
     */
    V2(2, HmacConst.KEY_SIGN_V2);

    /**
     * 版本号：1/2
     */
    private final Integer version;
    /**
     * http请求头中的签名Header
     */
    private final String signHeader;

    private static final List<XopHmacVersionEnum> VERSION_LIST = Stream.of(XopHmacVersionEnum.values())
        .sorted(Comparator.comparing(XopHmacVersionEnum::getVersion)).collect( Collectors.toList());

    XopHmacVersionEnum(Integer version, String signHeader) {
        this.version = version;
        this.signHeader = signHeader;
    }

    /**
     * 从map中去除key为签名Header的条目
     * @param map Map
     */
    public static void clearSignFromMap(Map<String, Object> map) {
        for (XopHmacVersionEnum versionEnum : VERSION_LIST) {
            map.remove(versionEnum.getSignHeader());
        }
    }

    public Integer getVersion() {
        return version;
    }

    public String getSignHeader() {
        return signHeader;
    }
}
