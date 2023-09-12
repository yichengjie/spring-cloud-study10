package com.yicj.sentinel.utils;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

@Slf4j
public class GlobalExceptionUtil {

    public static SentinelClientHttpResponse handleBlock(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        String resource = ex.getRule().getResource();
        RestResponse<Void> response = RestResponse.error("-1", "===被限流啦===");
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            log.error("序列化数据出错了", e);
        }
        return null;
    }

    public static SentinelClientHttpResponse handleFallback(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        RestResponse<Void> response = RestResponse.error("-2", "===被异常降级啦===");
        try {
            return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            log.error("序列化数据出错了", e);
        }
        return null;
    }
}
