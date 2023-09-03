package com.yicj.common.utils;

import java.util.UUID;

/**
 * @author yicj
 * @date 2023年09月03日 8:10
 */
public class CommonUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "") ;
    }

    public static void sleepQuiet(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
