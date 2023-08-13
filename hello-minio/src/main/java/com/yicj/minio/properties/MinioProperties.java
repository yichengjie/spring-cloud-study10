package com.yicj.minio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: yicj
 * @date: 2023/8/13 9:08
 */
@Data
//@Component
//@PropertySource(value = "classpath:minio.yml"
//        , encoding = "UTF-8")
@PropertySource(value = {"classpath:minio.properties"})
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String endpoint ;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
