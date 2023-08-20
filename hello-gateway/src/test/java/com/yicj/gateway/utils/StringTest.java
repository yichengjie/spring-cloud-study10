package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringTest {

    @Test
    public void match(){
        String value = "666" ;
        boolean flag = value.matches("\\d+");
        log.info("flag : {}", flag);
    }
}
