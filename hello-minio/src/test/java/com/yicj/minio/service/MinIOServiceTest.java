package com.yicj.minio.service;

import com.yicj.minio.MinioApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author: yicj
 * @date: 2023/8/13 9:55
 */
@Slf4j
@SpringBootTest(classes = MinioApplication.class)
public class MinIOServiceTest {

    @Autowired
    private MinioService minioService  ;

    @Test
    public void uploadLocalFile() throws Exception{
        String localPath = "D:\\data\\minio-1.png" ;
        String remotePath = "test.png";
        String retUrl = minioService.upload(localPath, remotePath);
        log.info("ret url : {}", retUrl);
    }

    @Test
    public void uploadInputStream() throws Exception{
        String localPath = "D:\\data\\minio-1.png" ;
        FileInputStream fis = new FileInputStream(localPath) ;
        String remotePath = "minio-1" + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
        String retUrl = minioService.upload(fis, remotePath);
        log.info("ret url : {}", retUrl);
        //  http://192.168.99.61:9000/dev/minio-199dfb924fc4946c997f34e1af8274f78.png
    }

    @Test
    public void getInputStream() throws Exception{
        String remotePath = "minio-1.png" ;
        String localPath = "D:\\data\\test.png" ;
        try (InputStream is = minioService.getInputStream(remotePath);
             OutputStream os = new FileOutputStream(localPath)){
            IOUtils.copy(is, os) ;
        }
    }

    @Test
    public void delete() throws Exception{
        String remotePath = "minio-199dfb924fc4946c997f34e1af8274f78.png" ;
        minioService.delete(remotePath);
    }
}
