package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023年09月01日 21:25
 */
@Slf4j
public class StreamTest {
    private List<ExchangeFilterFunction> filters;

    @Test
    @SuppressWarnings("all")
    public void reduce(){
        //filters.stream().reduce((fun, other) -> fun.andThen(other)) ;
        //Student stu1 = new Student(18) ;
        //Student stu2 = new Student(20) ;
        StudentAdd studentAdd = Student::andThen;
        StudentAdd studentAdd2 = ((stu1, stu2) -> stu1.andThen(stu2));
        int value = studentAdd.addStudent(new Student(1), new Student(2));

        //
        Function<Student, Function<Student, Integer>> studentAdd3 = stu1 -> {
            return stu2 -> stu1.andThen(stu2) ;
        } ;

        Integer value3 = studentAdd3.apply(new Student(1)).apply(new Student(2));

        log.info("value : {}", value);
        log.info("value3 : {}", value3);
    }





    interface StudentAdd{
        int addStudent(Student stu1, Student stu2) ;
    }

    class Student{
        private int num ;

        public Student(int num){
            this.num = num ;
        }


        int andThen(Student other){

            return this.num + other.num ;
        }
    }

}
