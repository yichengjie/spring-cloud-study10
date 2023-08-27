1. 健康检查接口地址：http://localhost:7071/actuator/health
2. mvn声明在pom中前面的优先级高与后面的
3. spring-cloud项目需要加添bootstrap依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    ```
4. 项目使用springdoc替代swagger组件
5. 如果需要负载均衡项目需要加入spring-cloud-starter-loadbalancer
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    </dependency>
    ```