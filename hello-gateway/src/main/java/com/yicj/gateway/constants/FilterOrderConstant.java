package com.yicj.gateway.constants;

/**
 * @author yicj
 * @date 2023年09月03日 13:22
 */
public interface FilterOrderConstant {
    /**
     * 请求体缓存过滤器
     */
    int CACHE_REQUEST_BODY = 0 ;

    /**
     * 登录或则注册过滤器
     */
    int LOGIN_OR_REGISTER = 1 ;

    /**
     * token验证过滤器
     */
    int TOKEN_CHECK = 2 ;
}
