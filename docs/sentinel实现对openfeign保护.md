1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    ```
2. yml添加配置
    ```yaml
    feign:
      sentinel:
        enabled: true
    ```
3. 编写feign接口并配置fallback属性
    ```java
    @FeignClient(value = "nacos-client-app"
            , contextId = "nacosHelloClient"
            , fallback = NacosHelloClientFallback.class
    )
    public interface NacosHelloClient {

        @GetMapping("/hello/index")
        String hello() ;

        @GetMapping("/hello/exception")
        String exception() ;
    }
    ```
4. 编写fallback实现代码
    ```java
    @Component
    public class NacosHelloClientFallback implements NacosHelloClient {

        @Override
        public String hello() {
            return "fallback hello ret value";
        }
        @Override
        public String exception() {
            return "fallback exception ret value";
        }
    }
    ```