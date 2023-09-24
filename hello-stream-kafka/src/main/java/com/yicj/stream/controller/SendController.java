package com.yicj.stream.controller;

import com.yicj.stream.model.dto.HelloMessage;
import com.yicj.stream.service.DefaultSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yicj
 * @date: 2023/9/23 19:09
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private DefaultSendService sendService ;

    @GetMapping("/message/{msg}")
    public String send(@PathVariable("msg") String msg){
        HelloMessage message = new HelloMessage(1, msg) ;
        sendService.sendMsg(message);
        return "success";
    }
}
