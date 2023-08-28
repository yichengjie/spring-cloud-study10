package com.yicj.gateway.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxTest2 {

    @Test
    public void groupBy(){
        Flux<StudentInfo> flux = this.initFluxStudent(1, 5);
        flux.map(StudentInfo::getId)
                .groupBy(i -> (i % 2 == 0) ?"even" :"odd")
                //.concatMap(g -> g.defaultIfEmpty(-1))
                //.map(String::valueOf)
                .subscribe(item -> item.subscribe(value -> log.info("key : {}, value :{}", item.key(), value)));

    }


    @Test
    public void groupBy2(){
        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                .subscribe(f -> f.subscribe(t->System.out.println(f.key() + " : " + t)));
    }


    private Flux<StudentInfo> initFluxStudent(int start, int end){
        return Flux.range(start, end)
                .map(item -> {
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setId(item);
                    studentInfo.setUsername("学生" + item);
                    studentInfo.setAddress("地址" + item);
                    return  studentInfo;
                }) ;
    }


    @Data
    class StudentInfo{

        private Integer id ;

        private String username ;

        private String address ;

    }
}
