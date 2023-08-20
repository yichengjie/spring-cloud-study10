package com.yicj.study.mvc.controller;

import com.yicj.study.mvc.model.form.HelloIndexForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(){
        return "hello webmvc index !" ;
    }

    @PostMapping("/downstream")
    public HelloIndexForm downstream(@RequestBody HelloIndexForm form){

        return form ;
    }

}
