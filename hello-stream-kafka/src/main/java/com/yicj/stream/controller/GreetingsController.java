package com.yicj.stream.controller;

import com.yicj.stream.model.dto.Greetings;
import com.yicj.stream.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {
    @Autowired
    private GreetingService greetingsService;

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@RequestParam("message") String message) {
        Greetings greetings = Greetings.builder()
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
        greetingsService.sendGreeting(greetings);
    }
}
