package com.yicj.user.controller;

import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author yicj
 * @date 2023年08月27日 15:01
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(
            @RequestHeader(value = "x-token", required = false) String xtoken,
            HttpServletRequest request){
        log.info("x-token value : {}", xtoken);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("----> header :  {} : {}",headerName, headerValue);
        }
        return "user service hello index !" ;
    }

    @GetMapping("/listAllUser")
    public RestResponse<List<UserVO>> listAllUser(){
        List<UserVO> retList = IntStream.range(1, 10000)
                .mapToObj(item -> {
                    UserVO vo = new UserVO();
                    vo.setId(UUID.randomUUID().toString());
                    vo.setUsername("username : " + item);
                    vo.setAddress("address : " + item);
                    return vo;
                }).toList();
        return RestResponse.success(retList) ;
    }

}
