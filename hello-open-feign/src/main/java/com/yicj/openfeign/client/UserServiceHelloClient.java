package com.yicj.openfeign.client;

import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 * UserServiceHelloClient
 * </p>
 *
 * @author yicj
 * @since 2023年11月26日 18:31
 */
@FeignClient(name = "hello-user-service"
        , contextId = "userServiceHelloClient"
        //, url = "127.0.0.1:8081"
)
public interface UserServiceHelloClient {

    @GetMapping("/user-service/hello/listAllUser")
    String listAllUserString() ;


    @GetMapping("/user-service/hello/listAllUser")
    RestResponse<List<UserVO>> listAllUser() ;
}
