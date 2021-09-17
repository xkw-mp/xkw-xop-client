/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xkw.xop.client.response.PagerDto;
import com.xkw.xop.client.response.XopResponse;
import kong.unirest.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * XopHttpClientTests
 *
 * @author LiuJibin
 * @date 2021/07/01
 */
public class XopHttpClientTests {

    // 凭证
    private static final String APP_ID = "114";
    private static final String SECRET = "hJiQ9O";
    // URL
    private static final String GET_URL = "/demosp/test/hello";
    private static final String POST_URL = "/demosp/demosp/request-details";

    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    @Test
    public void testGetRequest() {
        // query参数一定要放入parameters!
        Map<String, Object> queryMap = new HashMap<>(4);
        queryMap.put("name", "test-id");
        queryMap.put("res_name", "formal_res_name");
        queryMap.put("list_count", 10);
        HttpResponse<String> response = getClient().get(GET_URL, queryMap);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPostRequest() {
        Map<String, Object> bodyParams = new HashMap<>(4);
        bodyParams.put("name", "test-id");
        bodyParams.put("res_name", "formal_res_name");
        bodyParams.put("list_count", 10);
        HttpResponse<String> response = getClient().post(POST_URL, null, bodyParams);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testParseResult() {
        String response = "{\"code\":200,\"msg\":\"success\",\"data\":{\"name\":\"demo name\",\"value\":20}}\n";
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

    private XopHttpClient getClient() {
        return new XopHttpClient.Builder()
            .appId(APP_ID)
            .secret(SECRET)
            .timeout(10).maxConnectionPerRoute(10)
            .build();
    }

}