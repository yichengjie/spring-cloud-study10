package com.yicj.gateway.utils;

import java.util.UUID;

/**
 * @author yicj
 * @date 2023年09月02日 22:51
 */
public class CommonUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "") ;
    }
}
