package com.yicj.springdoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yicj
 * @date: 2023/6/17 11:03
 */
@Tag(name = "hello", description = "hello controller 描述")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Operation(summary = "hello", description = "hello的首页")
    @GetMapping("/index/{name}")
    public String hello(@PathVariable("name") String name){

        return "hello, " + name ;
    }
}
