package com.yicj.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: yicj
 * @date: 2023/6/17 11:18
 */
@Data
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private UserProperties user ;
}
