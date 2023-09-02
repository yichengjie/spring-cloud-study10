package com.yicj.webflux;

import com.yicj.webflux.repository.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * @author yicj
 * @date 2023年08月26日 19:52
 */
@Slf4j
public class FluxTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Test
    public void reduce(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(new ArrayList<Integer>(), (list, item) -> {
            list.add(item) ;
            return list ;
        }).subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce2(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(0, Integer::sum)
                .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce3(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(Integer::sum)
                .subscribe(value -> log.info("value : {}", value)) ;
    }


    @Test
    public void switchIfEmpty(){
        //List<String> idList = Arrays.asList("1", "2", "3","4") ;
        //List<String> idList = Arrays.asList("7","8") ;
        List<String> idList = Arrays.asList("1","9") ;
        Flux.fromIterable(idList)
                .flatMap(id -> findPersonById(id, false))
                .switchIfEmpty(Mono.just(new PersonEntity()))
                //.publishOn(Schedulers.boundedElastic())
                .subscribe(item -> log.info("value : {}", item)) ;
    }

    @Test
    public void timeout(){
        Flux<PersonEntity> flux = this.listAllPerson(100, false);
        flux
            .timeout(Duration.ofMillis(800))
            //.onErrorResume()
            .map(PersonEntity::getId)
                .flatMap(id -> findPersonById(id, false))
            .switchIfEmpty(Mono.just(new PersonEntity()))
            //.subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .subscribe(item -> log.info("value : {}", item)) ;
        sleep(2000);
    }


    @Test
    public void onErrorResume(){
        Flux<PersonEntity> flux = this.listAllPerson(100, true);
        flux
            .timeout(Duration.ofMillis(800))
            // 从出错后面的元素重新赋值
            .onErrorResume(throwable -> {
                PersonEntity p1 = new PersonEntity();
                p1.setId("11");
                PersonEntity p2 = new PersonEntity();
                p2.setId("12");
                return Flux.fromIterable(Arrays.asList(p1,p2)) ;
            })
//            .log()
            .map(PersonEntity::getId)
            .flatMap(id -> findPersonById(id, false))
            .switchIfEmpty(Mono.just(new PersonEntity()))
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .subscribe(
                item -> log.info("value : {}", item),
                error -> log.error("处理出错: ", error)
            ) ;
        sleep(2000);
    }


    @Test
    public void onErrorResume2(){
        PersonEntity p1 = new PersonEntity() ;
        p1.setId("1");
        //
        PersonEntity p2 = new PersonEntity() ;
        p2.setId("3");
        //
        PersonEntity p3 = new PersonEntity() ;
        p3.setId("4");
        //
        PersonEntity p4 = new PersonEntity() ;
        p4.setId("5");
        //
        Flux<PersonEntity> flux = Flux.just(p1, p2, p3, p4);
        flux
            // 从出错后面的元素重新赋值
            .onErrorResume(throwable -> {
                PersonEntity p11 = new PersonEntity();
                p11.setId("11");
                PersonEntity p12 = new PersonEntity();
                p12.setId("12");
                return Flux.fromIterable(Arrays.asList(p11,p12)) ;
            })
//            .log()
            .map(PersonEntity::getId)
            .flatMap(id -> findPersonById(id, true))
            // 上一步出错之后执行的逻辑，后续元素将不再继续
            //.onErrorResume(throwable -> Mono.just(new PersonEntity()))
            //.switchIfEmpty(Mono.just(new PersonEntity()))
            .subscribe(
                item -> log.info("value : {}", item),
                error -> log.error("处理出错: ", error)
            ) ;
    }



    @Test
    public void handle(){
        List<String> list = Arrays.asList("1", "2", "3");
        Flux<String> flux = Flux.fromIterable(list);
        flux.handle((item, sink) -> {
            List<String> tmpList = new ArrayList<>(){{
                add("1") ;
                add("3") ;
            }} ;
            if (tmpList.contains(item)){
                sink.next(Integer.parseInt(item));
            }
        })
        .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void thread() throws InterruptedException {
        final Mono<String> mono = Mono.just("hello ");
        Thread thread = new Thread(() -> {
            mono.map(item -> "thread " + item)
                    .subscribe(item -> log.info("value: {}", item));
        });
        thread.start();
        thread.join();
    }

    @Test
    public void onError(){
        Flux<String> flux = Flux.just("1", "2", "3", "4");
        flux
            .timeout(Duration.ofMillis(800))
            // 从出错后面的元素重新赋值
//            .log()
            .flatMap(id -> findPersonById(id, true))
            .switchIfEmpty(Mono.just(new PersonEntity()))
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .subscribe(
                item -> log.info("value : {}", item),
                error -> log.error("处理出错: ", error)
            ) ;
        sleep(2000);
    }

    @Test
    public void publishOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    log.info("map item : {}", i);
                    return 10 + i ;
                })
                .publishOn(s)
                .map(i -> "value " + i);
        Thread thread = new Thread(() -> flux.subscribe(value -> log.info("value : {}",value)));
        thread.start();
        thread.join();
    }


    @Test
    public void subscribeOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    log.info("map item : {}", i);
                    return 10 + i ;
                })
                .subscribeOn(s)
                .map(i -> "value " + i);
        Thread thread = new Thread(() -> flux.subscribe(item -> log.info("item value : {}",item)));
        thread.start();
        thread.join();
    }


    @Test
    public void next(){
        String id = "3" ;
        Flux<PersonEntity> flux = Flux.fromIterable(this.initPersonList());
        flux.filter(item -> item.getId().equals(id))
                .log()
                .next()
                .subscribe(value -> log.info("value : {}", value)) ;
        //
        Mono<PersonEntity> byIdMono = this.findPersonById("7", false);
        byIdMono.switchIfEmpty(Mono.just(new PersonEntity()))
                .subscribe(value -> log.info("value : {}", value)) ;
        log.info("entity : {}, empty mono: {}", byIdMono, Mono.empty());
    }


    @Test
    public void onErrorReturn(){
        PersonEntity errorReturnPerson = new PersonEntity() ;
        errorReturnPerson.setId("99");
        errorReturnPerson.setUsername("error return person");
        errorReturnPerson.setAddress("xxx");
        Flux.range(1,10)
            .map(String::valueOf)
            .flatMap(item -> this.findPersonById(item, true))
            .onErrorReturn(errorReturnPerson)
            .subscribe(item -> log.info("item :{}", item)) ;
    }

    @Test
    public void onErrorResume3(){
        PersonEntity errorReturnPerson = new PersonEntity() ;
        errorReturnPerson.setId("88");
        errorReturnPerson.setUsername("error resume person");
        errorReturnPerson.setAddress("yyy");
        Flux.range(1,10)
                .map(String::valueOf)
                .flatMap(item -> this.findPersonById(item, true))
                .onErrorResume(error -> {
                    log.error("error msg : ", error);
                    return Mono.just(errorReturnPerson) ;
                })
                .subscribe(item -> log.info("item :{}", item)) ;
    }


    @Test
    public void create(){
        Flux<PersonEntity> flux = this.listAllPerson(1,false);
        flux.subscribe(item -> log.info("item -> {}",item)) ;
    }

    @Test
    public void fromIterable(){
        List<String> list = Arrays.asList("1", "2", "3", null);
        list.forEach(item -> log.info("value : {}", item));
        // stream中null能正常输出，但是flux null会报错
        //
        Flux.fromIterable(list)
                .subscribe(value -> log.info("item : {}", value));
    }


    private Flux<PersonEntity> listAllPerson(long timeout, boolean error){
        return Flux.create(fluxSink -> {
            new Thread(()-> {
                log.info("DB 查询person 列表数据..........");
                sleep(timeout);
                List<PersonEntity> list = this.initPersonList();
                try{
                    for (int i = 0 ; i < list.size(); i++){
                        if (i == 2 && error){
                            //throw new RuntimeException("生成第三个元素出错!!") ;
                            int v = 1/0 ;
                        }
                        fluxSink.next(list.get(i)) ;
                    }
                    fluxSink.complete();
                }catch (Exception e){
                    fluxSink.error(e);
                }
            }).start();
            //CompletableFuture.runAsync(() -> {
            //}, executorService) ;
        }) ;
        // 下面这段超时不会生效，因为同一个线程中
//        return Flux.defer(() -> {
//            log.info("DB 查询person 列表数据..........");
//            sleep(1000);
//            List<PersonEntity> personEntities = this.initPersonList();
//            return Flux.fromIterable(personEntities) ;
//        }) ;
    }


    @Test
    public void findError(){
        this.findPersonById("12", true)
                .subscribe(value -> log.info("value : {}", value));
    }

    private Mono<PersonEntity> findPersonById(String id, boolean error){
        return Mono.defer(() -> {
            PersonEntity entity = this.initPersonList().stream()
                    .filter(item -> id.equals(item.getId()))
                    .findAny()
                    .orElse(null);
            if (entity == null){
                if (error){
                    throw new RuntimeException("未查询到id为"+id+"的数据!") ;
                }
                return Mono.empty() ;
            }
            log.info("查询到id为{}详情数据..........", id);
            return Mono.just(entity) ;
//          Flux<PersonEntity> flux = Flux.fromIterable() ;
//          return flux.filter(item -> id.equals(item.getId()))
//                  .next() ;
        }) ;
    }


    private void sleep(long timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PersonEntity> initPersonList(){
        PersonEntity p1 = new PersonEntity() ;
        p1.setId("1");
        p1.setUsername("张三");
        p1.setAddress("BJS");
        //
        PersonEntity p2 = new PersonEntity() ;
        p2.setId("2");
        p2.setUsername("李四");
        p2.setAddress("BJS");
        //
        PersonEntity p3 = new PersonEntity() ;
        p3.setId("4");
        p3.setUsername("王五");
        p3.setAddress("SHA");
        //
        PersonEntity p4 = new PersonEntity() ;
        p4.setId("5");
        p4.setUsername("赵六");
        p4.setAddress("BJS");
        //
        return Arrays.asList(p1, p2, p3, p4) ;
    }

}
