package com.yicj.sentinel.utils;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class GlobalExceptionUtil {

    public static ClientHttpResponse handleBlock(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        String resource = ex.getRule().getResource();
        RestResponse<Void> response = RestResponse.error("-1", "===被限流啦===");
        return new SentinelClientHttpResponse(JSON.toJSONString(response));
    }

    public static ClientHttpResponse fallback(/*
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException ex*/
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
        RestResponse<Void> response = RestResponse.error("-2", "===被异常降级啦===");
        return new SentinelClientHttpResponse(JSON.toJSONString(response));
    }
}
