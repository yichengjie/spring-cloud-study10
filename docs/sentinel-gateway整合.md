1. 启动dashboard添加参数-Dcsp.sentinel.app.type=1
    ```cmd
    java -jar -Dcsp.sentinel.app.type=1 sentinel-dashboard-1.8.6.jar
    ```
2. 引入依赖
    ```xml
     <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-spring-cloud-gateway-adapter</artifactId>
    </dependency>
    ```
3. 将 spring.cloud.sentinel.filter.enabled 配置项置为 false