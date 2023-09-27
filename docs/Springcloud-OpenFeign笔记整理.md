#### feign 高级配置
1. yml配置
    ```yaml
    feign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000
            loggerLevel: basic
          nacos-provider1:
            connectTimeout: 5000
            readTimeout: 5000
            loggerLevel: full
    #        errorDecoder: com.example.SimpleErrorDecoder
    #        retryer: com.example.SimpleRetryer
            defaultQueryParamters:
              query: queryValue
            defaultRequestHeaders:
              header: headerValue
            requestInterceptors:
    #          - com.example.FooRequestInterceptor
    #          - com.example.BarRequestInterceptor
            decore404: false
    #        encoder: com.example.SimpleEncoder
    #        decoder: co.example.SimpleDecoder
    #        contract: com.example.SimpleContract
    #        capabilities:
    #          - com.example.FooCapability
    #          - com.example.BarCapability
            metrics.enabled: false
    ```
2. @FeignClient属性configuration配置可以参考FeignClientsConfiguration修改
#### OpenFeign的二次改造
1. 认识MicrometerCapability
2. 基于Capability的扩展机制
3. 项目引入依赖
   ```xml
   <dependency>
       <groupId>io.github.openfeign</groupId>
       <artifactId>feign-micrometer</artifactId>
   </dependency>
   ```
4. 可参考MicrometerCapability的实现，自定义覆盖默认