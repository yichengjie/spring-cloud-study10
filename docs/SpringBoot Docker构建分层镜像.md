1. plugin配置layers
    ```xml
     <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <executions>
            <execution>
                <id>repackage</id>
                <goals>
                    <goal>repackage</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <image>
               <name>docker.io/yicj/${project.artifactId}:${project.version}</name>
            </image>
            <layers>
                <!--开启分层镜像索引-->
                <enabled>true</enabled>
            </layers>
        </configuration>
    </plugin>
    ```
2. package后解压jar从查看BOOT-INF/layers.idx文件
3. 编写Dockerfile文件(copy路径根据RUN java -Djarmode.. 执行结果得出的)
    ```dockerfile
    FROM openjdk:8u212-jdk-stretch as builder
    
    WORKDIR application
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} application.jar
    RUN java -Djarmode=layertools -jar application.jar extract
    FROM openjdk:8u212-jdk-stretch
    
    WORKDIR application
    COPY --from=builder application/dependencies/ ./
    COPY --from=builder application/spring-boot-loader/ ./
    COPY --from=builder application/snapshot-dependencies/ ./
    COPY --from=builder application/snapshot-dependencies/ ./
    COPY --from=builder application/application/ ./
    ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
    ```
4. 构建镜像: ```docker build -t yicj/module-user .```
5. 运行镜像: ```docker run -p 8080:8080 yicj/module-user```
### 其他
1. 执行命令解压jar查看目录：```java -Djarmode=layertools -jar hello-xxx-1.0-SNAPSHOT extract```