package com.yicj.minio.controller;

import com.yicj.minio.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;

/**
 * @author: yicj
 * @date: 2023/8/13 9:50
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioService minioService ;

    @PostMapping("/upload")
    public HashMap<String, String> upload(@RequestParam(name = "file", required = false) MultipartFile file) throws Exception{
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = minioService.upload(file.getInputStream(), fileName);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url",url);
        map.put("fileName",fileName);
        return map;
    }


    @GetMapping("/remove")
    public String remove(String fileName) throws Exception{
        minioService.delete(fileName) ;
        return "success" ;
    }

}
