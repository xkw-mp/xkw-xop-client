/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.google.gson.reflect.TypeToken;
import com.xkw.xop.client.response.PagerDto;
import com.xkw.xop.client.response.XopHttpResponse;
import com.xkw.xop.client.response.XopResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XopHttpClientTests
 *
 * @author LiuJibin
 */
public class XopHttpClientTests extends XopHttpClientTestBase {

    @Test
    public void testGetRequest() {
        // query参数一定要放入queryParams中
        Map<String, Object> queryParams = new HashMap<>(4);
        queryParams.put("id", "1");
        XopHttpResponse<String> response = CLIENT.get(GET_URL, queryParams);
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
        XopHttpResponse<String> response = CLIENT.post(POST_URL, null, bodyParams);
        // 请求失败处理
        if (!response.isSuccess()) {
            System.out.println("请求失败， 错误码： " + response.getStatus() + "requestId：" + response.getRequestId());
            Assert.fail("请求失败");
        }
        System.out.println("请求成功，结果：" + response.getBody());
        // 解析忽略
    }

    @Test
    public void testParseResult() {
        String response = "{\"code\":200,\"msg\":\"success\",\"data\":{\"name\":\"demo name\",\"value\":20}}";
        XopResponse<DemoDto> result = GSON.fromJson(response, new TypeToken<XopResponse<DemoDto>>(){}.getType());
        Assert.assertNotNull(result.getMsg());
        Assert.assertNotNull(result.getCode());
        System.out.println("Response Body:\n " + result.getData());
    }

    @Test
    public void testPager() {
        String response = "{\"code\":200,\"msg\":\"success\",\"data\":{\"list\":[{\"name\":\"demo name\",\"value\":20},{\"name\":\"demo2\",\"value\":17}],\"pager\":{\"current_page_no\":1,\"page_size\":10,\"total_page_no\":4,\"total_count\":35}}}";
        XopResponse<PagerDto<DemoDto>> result = GSON.fromJson(response, new TypeToken<XopResponse<PagerDto<DemoDto>>>(){}.getType());
        Assert.assertNotNull(result.getMsg());
        Assert.assertNotNull(result.getCode());
        Assert.assertEquals(2, result.getData().getList().size());
        Integer expectedCurrentPageNo = 1;
        Assert.assertEquals(expectedCurrentPageNo, result.getData().getPager().getCurrentPageNo());
    }
}