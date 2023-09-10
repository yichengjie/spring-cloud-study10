package com.yicj.study.mvc.controller;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/initBinder")
public class InitBinderController {

    @GetMapping("/index")
    private String index(Date date) {
        System.out.println("date = " + date);
        return "RequestInitBindDemo";
    }

    @InitBinder
    public void dateTypeBinder(WebDataBinder webDataBinder){
        //往数据绑定器中添加一个DateFormatter日期转化器。
        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-mm-dd"));
    }
}