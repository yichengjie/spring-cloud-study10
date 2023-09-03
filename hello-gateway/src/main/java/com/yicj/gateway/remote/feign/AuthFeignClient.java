package com.yicj.gateway.remote.feign;

import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.common.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author yicj
 * @date 2023年09月03日 11:32
 */
@FeignClient(name = CommonConstants.AUTH_SERVICE_NAME, contextId = "AuthFeignClient")
public interface AuthFeignClient {

    @PostMapping(CommonConstants.AUTH_LOGIN_PATH)
    RestResponse<TokenVO> login(@RequestBody LoginForm form) ;

    @PostMapping(CommonConstants.AUTH_REGISTER_PATH)
    RestResponse<TokenVO> register(@RequestBody RegisterForm form) ;

    @GetMapping(CommonConstants.AUTH_FIND_BY_TOKEN_PATH)
    RestResponse<UserVO> findByToken(@RequestHeader("token") String token) ;

}
