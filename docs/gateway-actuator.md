1. 添加actuator依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
2. 配置gateway的actuator开放(spring-cloud:2022.0.4版本配置，早期版本有差异)
    ```yml
    management:
      endpoints:
        web:
          exposure:
            include: "*"
      endpoint:
        health:
          show-details: always
        # 重点在这里,默认为false,所以没有gateway相关端点,打开后就可以访问
        gateway:
          enabled: true
    ```
3. 访问地址：http://localhost:8080/actuator/gateway/routes查看所有的routes信息