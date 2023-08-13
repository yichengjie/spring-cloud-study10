package com.yicj.minio.properties;

import com.yicj.minio.MinioApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: yicj
 * @date: 2023/8/13 9:16
 */
@Slf4j
@SpringBootTest(classes = MinioApplication.class)
public class MinioPropertiesTest {

    @Autowired
    private MinioProperties minioProperties ;

    @Test
    public void hello(){

        log.info("properties : {}", minioProperties);
    }

}
