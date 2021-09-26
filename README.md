
# 学科网开放平台-XOP HTTP客户端

## 学科网开放平台-XOP简介

学科网开放平台是学科网打造的K12教育企业服务平台。本平台致力于开放多年沉淀的优质备授课资源、精品题库、资源、视频、工具和能力、输出元数据标准，解决客户在题库和资源库上的迫切需求。通过解决行业内对内容相关需求而赋能整个K12教育行业，支持基于用户和基础数据打通的深度整合，实现合作共赢。

学科网开放平台的门户：https://open.xkw.com

## 快速开始

引入Maven依赖

```
    <dependencies>
        <dependency>
            <groupId>com.xkw.xop</groupId>
            <artifactId>xkw-xop-client</artifactId>
            <version>1.0.2</version>
        </dependency>
    </dependencies>
```

创建XopHttpClient

```
XopHttpClient client =
    new XopHttpClient.Builder()
       .appId(APP_ID)
       .secret(SECRET)
       .timeout(10).maxConnectionPerRoute(10)
       .build();
```

请求接口

```
   Map<String, Object> queryMap = new HashMap<>(4);
   queryMap.put("name", "test-id");
   queryMap.put("res_name", "formal_res_name");
   queryMap.put("list_count", 10);
   HttpResponse<String> response = getClient().get(GET_URL, queryMap);
```

更多内容请参考：XopHttpClientTests

## 注意事项

因为URI和query参数需要分别参与签名，请在使用接口调用接口时，分别传入URI和query参数。

## XOP接口请求签名

XOP接口使用HMAC签名算法




