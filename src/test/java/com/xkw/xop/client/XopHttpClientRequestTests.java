/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.google.gson.reflect.TypeToken;
import com.xkw.xop.client.request.XopHttpRequest;
import com.xkw.xop.client.response.XopHttpResponse;
import com.xkw.xop.client.response.XopResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XopHttpClientRequestTests
 * description
 *
 * @author LiuJibin
 */
public class XopHttpClientRequestTests extends XopHttpClientTestBase {

    @Test
    public void testGetRequest() {
        // query参数一定要放入queryParams中
        Map<String, Object> queryParams = new HashMap<>(4);
        queryParams.put("id", "1");
        XopHttpRequest request = CLIENT.getRequest().uri(GET_URL).queryParams(queryParams);
        XopHttpResponse<String> response = CLIENT.sendRequest(request);
        // 请求失败处理
        if (!response.isSuccess()) {
            System.out.println("请求失败， 错误码： " + response.getStatus() + "requestId：" + response.getRequestId());
            Assert.fail("请求失败");
        }
        System.out.println("请求成功，结果：");
        XopResponse<AreaDto> result = GSON.fromJson(response.getBody(), new TypeToken<XopResponse<AreaDto>>(){}.getType());
        System.out.println("Area: " + result.getData());
    }

    @Test
    public void testPostRequest() {
        // 举一反三推题
        Map<String, Object> bodyParams = new HashMap<>(4);
        // 小学语文
        bodyParams.put("course_id", 1);
        // 知识点，基础知识
        List<Integer> kpIds = new ArrayList<>();
        kpIds.add(65443);
        bodyParams.put("kpoint_ids", kpIds);
        // 1条数据
        bodyParams.put("count", 1);
        XopHttpRequest request = CLIENT.postRequest().uri(POST_URL).body(bodyParams);
        XopHttpResponse<String> response = CLIENT.sendRequest(request);
        // 请求失败处理
        if (!response.isSuccess()) {
            System.out.println("请求失败， 错误码： " + response.getStatus() + "requestId：" + response.getRequestId());
            Assert.fail("请求失败");
        }
        System.out.println("请求成功，结果：" + response.getBody());
        // 解析忽略
    }
}
