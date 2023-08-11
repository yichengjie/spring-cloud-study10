package com.yicj.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 16:39
 */
@Slf4j
public class CommonUtil {

    public static <T> void printList(List<T> list, int size){
        if (CollectionUtils.isEmpty(list)){
            log.info("list is empty !");
            return;
        }
        log.info("list size : {}", list.size());
        list.stream().limit(size).forEach(item -> log.info("====> item : {}", item));
    }
}
