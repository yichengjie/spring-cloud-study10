package com.yicj.webflux.service;

import com.yicj.webflux.model.form.ListPersonForm;
import com.yicj.webflux.repository.entity.PersonEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月26日 17:32
 */
public interface PersonService {

    Mono<PersonEntity> save(PersonEntity entity) ;

    Mono<PersonEntity> findById(String id) ;

    Flux<PersonEntity> list(ListPersonForm form) ;
}
