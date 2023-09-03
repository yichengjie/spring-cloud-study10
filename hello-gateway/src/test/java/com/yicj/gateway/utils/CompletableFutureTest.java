package com.yicj.gateway.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author yicj
 * @date 2023年09月03日 16:13
 */
@Slf4j
public class CompletableFutureTest {
    @Test
    public void hello(){
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("hello world!");
            CommonUtil.sleepQuiet(500);
            return 1;
        });
        CommonUtil.sleepQuiet(1000);
    }
}
