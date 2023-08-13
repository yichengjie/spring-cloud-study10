package com.yicj.minio.service;

import com.yicj.minio.properties.MinioProperties;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;

/**
 * @author: yicj
 * @date: 2023/8/13 9:38
 */
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient ;

    @Autowired
    private MinioProperties minioProperties ;

    /**
     * 本地文件上传
     * @param localPath 本地路径
     * @param remotePath 远程路径
     * @return 可访问地址
     * @throws Exception
     */
    public String upload(String localPath, String remotePath) throws Exception {
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(localPath)
                .filename(remotePath)
                .build() ;
        minioClient.uploadObject(uploadObjectArgs) ;
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + remotePath ;
    }


    public String upload(InputStream is, String remotePath) throws Exception {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(remotePath)
                .stream(is, -1, 10485760)
                .build();
        minioClient.putObject(args) ;
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + remotePath ;
    }


    public void delete(String remotePath) throws Exception{
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(remotePath)
                .build() ;
        minioClient.removeObject(args) ;
    }

    public InputStream getInputStream(String remotePath) throws Exception{
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(remotePath)
                .build() ;
        return minioClient.getObject(args) ;
    }

}
