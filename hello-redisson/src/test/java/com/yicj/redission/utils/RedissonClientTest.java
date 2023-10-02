package com.yicj.redission.utils;

import com.yicj.resission.HelloRedissonApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: yicj
 * @date: 2023/9/24 14:52
 */
@Slf4j
@SpringBootTest(classes = HelloRedissonApplication.class)
public class RedissonClientTest {

    @Autowired
    private RedissonClient client ;

    @Test
    public void hello(){
        RLock lock = client.getLock("anyLock") ;
        try {
            lock.lock();
        }finally {
            lock.unlock();
        }
    }
}
