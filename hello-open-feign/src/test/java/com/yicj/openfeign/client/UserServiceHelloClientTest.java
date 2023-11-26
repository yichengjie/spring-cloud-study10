package com.yicj.openfeign.client;

import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.UserVO;
import com.yicj.openfeign.OpenFeignApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * UserServiceHelloClientTest
 * </p>
 *
 * @author yicj
 * @since 2023年11月26日 18:34
 */
@Slf4j
@SpringBootTest(classes = OpenFeignApplication.class)
public class UserServiceHelloClientTest {

    @Autowired
    private UserServiceHelloClient userServiceHelloClient ;

    @Test
   public void listAllUserString(){
        String retValue = userServiceHelloClient.listAllUserString();
        log.info("ret value: {}", retValue);
    }

    @Test
    public void listAllUser(){
        RestResponse<List<UserVO>> response = userServiceHelloClient.listAllUser();
        log.info("ret value: {}", response);
    }
}
