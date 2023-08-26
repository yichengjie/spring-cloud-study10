package com.yicj.webflux.service.impl;

import com.yicj.webflux.model.form.ListPersonForm;
import com.yicj.webflux.model.form.SavePersonForm;
import com.yicj.webflux.repository.entity.PersonEntity;
import com.yicj.webflux.service.PersonService;
import com.yicj.webflux.utils.CommonUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月26日 17:34
 */
@Service
public class MemoryPersonServiceImpl implements PersonService {

    private final List<PersonEntity> personList = new ArrayList<>() ;

    @Override
    public Mono<PersonEntity> save(SavePersonForm form) {
        return Mono.defer(() -> {
            PersonEntity entity = new PersonEntity();
            entity.setId(CommonUtil.uuid());
            entity.setUsername(form.getUsername());
            entity.setAddress(form.getAddress());
            personList.add(entity);
            return Mono.just(entity) ;
        }) ;
    }

    @Override
    public Mono<PersonEntity> findById(String id) {
        return Mono.defer(() ->
            Flux.fromIterable(personList)
            .filter(item -> item.getId().equals(id))
            .next()
        ) ;
    }

    @Override
    public Flux<PersonEntity> list(ListPersonForm form) {
        return Flux.defer(() ->{
            if (form ==null){
                return Flux.fromIterable(personList) ;
            }
            return Flux.fromIterable(personList)
                    .filter(item -> item.getUsername().equals(form.getUsername())) ;
        }) ;
    }
}
