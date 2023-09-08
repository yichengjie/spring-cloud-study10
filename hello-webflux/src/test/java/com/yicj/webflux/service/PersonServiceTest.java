package com.yicj.webflux.service;

import com.yicj.common.model.form.ListPersonForm;
import com.yicj.webflux.HelloWebFluxApplication;
import com.yicj.webflux.repository.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author yicj
 * @date 2023年09月02日 10:08
 */
@Slf4j
@SpringBootTest(classes = HelloWebFluxApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService ;

    @Test
    public void hello(){
        personService.list(new ListPersonForm())
                .flatMap(item -> personService.findById(item.getId()))
                .switchIfEmpty(Mono.just(new PersonEntity()))
                .take(5)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(value -> log.info("value : {}", value)) ;
    }

}
