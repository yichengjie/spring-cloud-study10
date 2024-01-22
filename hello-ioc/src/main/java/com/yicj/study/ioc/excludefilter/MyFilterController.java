package com.yicj.study.ioc.excludefilter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023/11/5 11:45
 */
@RestController
public class MyFilterController {

    @GetMapping("/filter/index")
    public String index(){
        return "MyFilterController index" ;
    }
}
