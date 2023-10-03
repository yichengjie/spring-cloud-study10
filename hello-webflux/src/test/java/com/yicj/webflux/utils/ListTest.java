package com.yicj.webflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.ListIterator;

/**
 * @author yicj
 * @date 2023年10月03日 9:33
 */
@Slf4j
public class ListTest {

    @Test
    public void hasPrevious() throws Exception{
        List<Integer> list = Flux.range(1, 0).collectList().block();
        log.info("list : {}", list);
        //---------------------------------//
        assert list != null;
        ListIterator<Integer> iterator = list.listIterator(list.size());
        if (iterator.hasPrevious()){
            Integer previous = iterator.previous() ;
            log.info("value : {}", previous);
        }else {
            log.info("This list is empty !");
        }
    }

}
