package com.yicj.rx.model;

import com.yicj.rx.enums.City;
import rx.Observable;

import java.time.LocalDateTime;

/**
 * @author: yicj
 * @date: 2023/9/10 10:28
 */
public class Vacation {

    private final City where;

    private final LocalDateTime when ;

    public Vacation(City where, LocalDateTime when){
        this.where = where ;
        this.when = when ;
    }

    public Observable<Weather> weather(){
        // ...
        return Observable.empty() ;
    }

    public Observable<Flight> cheapFlightFrom(City from){

        // ...
        return Observable.empty() ;
    }

    public Observable<Hotel> cheapHotel(){
        // ...
        return Observable.empty() ;
    }
}
