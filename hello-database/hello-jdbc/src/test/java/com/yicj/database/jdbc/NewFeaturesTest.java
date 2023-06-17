package com.yicj.database.jdbc;

import com.yicj.database.jdbc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author: yicj
 * @date: 2023/6/17 13:13
 */
@Slf4j
public class NewFeaturesTest {

    @Test
    public void feature1(){
        User user = new User("yicj", "test") ;
        log.info("user : {}", user);
    }

    @Test
    public void feature2(){
        var week = 7 ;
        var momo = "" ;
        switch (week){
            case 1 -> momo = "星期日，休息" ;
            case 2,3,4,5,6 -> momo ="工作日" ;
            case 7 -> momo ="星期六，休息" ;
            default -> throw new IllegalArgumentException("无效的日期") ;
        }
        log.info("week : {}", momo);
    }
}
