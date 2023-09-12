1. pom添加依赖
    ```xml
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-datasource-nacos</artifactId>
    </dependency>
    ```
2. 添加nacos配置
    ```yaml
    spring:
      cloud:
        sentinel:
          datasource:
            ds:
              nacos:
                server-addr: 127.0.0.1:8848
                # namespace: public  # 注意public空间下不能显示的设置为public否则获取不到配置
                dataId: sentinel-client-app-sentinel.json
                groupId: DEFAULT_GROUP
                data-type: json
                rule-type: flow
    ```
3. 编写Controller测试Sentinel
    ```java
    @RestController
    @RequestMapping("/sentinel/datasource")
    public class SentinelDatasourceController {
    
        @GetMapping("/index")
        @SentinelResource("sentinelDatasourceIndex")
        public String index(){
    
            return "hello world index" ;
        }
    }
    ```
4. nacos中添加dataId为sentinel-client-app-sentinel.json的配置
    ```json
    [
      {
        "resource": "sentinelDatasourceIndex",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
      }
    ]
    ```
5. 注意如果在namespace为public下创建的配置，则编写配置时namespace必须为空
