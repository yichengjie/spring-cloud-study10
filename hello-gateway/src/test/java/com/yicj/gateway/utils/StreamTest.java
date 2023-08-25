package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yicj
 * @date 2023年08月25日 9:58
 */
@Slf4j
public class StreamTest {

    @Test
    public void reduce(){
        ArrayList<Integer> retList = Arrays.asList(1, 2, 3, 4).stream()
                .reduce(new ArrayList<>(), (list, item) -> {
                    list.add(item);
                    return list;
                }, (list1, list2) -> {
                    //list1.addAll(list2);
                    //return list1;
                    list2.addAll(list1) ;
                    return list2 ;
                });
        log.info("list : {}", retList);
    }
}
