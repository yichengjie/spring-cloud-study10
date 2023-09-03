package com.yicj.gateway.remote.feign;

import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.TokenVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yicj
 * @date 2023年09月03日 11:32
 */
@FeignClient(name = CommonConstants.AUTH_SERVICE_NAME, contextId = "AuthFeignClient")
public interface AuthFeignClient {

    @PostMapping(CommonConstants.AUTH_LOGIN_PATH)
    TokenVO login(@RequestBody LoginForm form) ;

    @PostMapping(CommonConstants.AUTH_REGISTER_PATH)
    TokenVO register(@RequestBody RegisterForm form) ;

}
