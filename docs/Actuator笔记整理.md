#### 自定义actuator
1. 编写Endpoint实现类
    ````java
    @Configuration
    @Endpoint(id = "my-endpoint")
    public class MyEndpoint {
        @ReadOperation
        public Map<String, Object> endpoint() {
            Map<String, Object> map = new HashMap<>(16);
            map.put("message", "this is my endpoint");
            return map;
        }
    }
    ````
2. 配置endpoint暴漏
    ```properties
    management.endpoints.web.exposure.include=my-endpoint
    ```
3. 访问地址：http://localhost:8083/hello-webmvc/actuator/my-endpoint
#### actuator原理
1. 添加spring-boot-starter-actuator
2. 按需开始：management.endpoints.web.exposure.include=*
3. 执行 *EndpointAutoConfiguration (EnvironmentEndpointAutoConfiguration、WebEndpointAutoConfiguration)
4. 构建WebMvcEndpointHandlerMapping
5. WebMvcEndpointManagementContextConfiguration