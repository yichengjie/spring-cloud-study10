package com.yicj.webflux.handler;

import com.yicj.webflux.model.form.ListPersonForm;
import com.yicj.webflux.model.form.SavePersonForm;
import com.yicj.webflux.repository.entity.PersonEntity;
import com.yicj.webflux.service.PersonService;
import com.yicj.webflux.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月26日 17:16
 */
@Component
public class HelloHandler {

    @Autowired
    private PersonService personService ;

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String idContent = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.findById(idContent), PersonEntity.class) ;
    }

    public Mono<ServerResponse> listPeople(ServerRequest serverRequest) {
        ListPersonForm form = serverRequest.queryParam("username")
                .map(username -> {
                    ListPersonForm temp = new ListPersonForm();
                    temp.setUsername(username);
                    return temp;
                })
                .orElse(null);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.list(form), PersonEntity.class) ;
    }

    public Mono<ServerResponse> createPerson(ServerRequest serverRequest) {
        // 上面这种写法会报错
//        return serverRequest.bodyToMono(SavePersonForm.class)
//                .map(form -> {
//                    PersonEntity entity = new PersonEntity();
//                    entity.setId(CommonUtil.uuid());
//                    entity.setUsername(form.getUsername());
//                    entity.setAddress(form.getAddress());
//                    return entity ;
//                })
//                .flatMap(entity -> personService.save(entity))
//                .flatMap(entity ->
//                        ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(entity, PersonEntity.class)
//                );
        Mono<PersonEntity> entityMono = serverRequest.bodyToMono(SavePersonForm.class)
            .map(form -> {
                PersonEntity entity = new PersonEntity();
                entity.setId(CommonUtil.uuid());
                entity.setUsername(form.getUsername());
                entity.setAddress(form.getAddress());
                return entity;
            })
            .flatMap(entity -> personService.save(entity));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(entityMono, PersonEntity.class) ;


    }
}
