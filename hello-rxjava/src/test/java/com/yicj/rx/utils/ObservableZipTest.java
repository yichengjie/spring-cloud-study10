package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import com.yicj.rx.enums.City;
import com.yicj.rx.model.Vacation;
import com.yicj.rx.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 10:37
 */
@Slf4j
public class ObservableZipTest {

    @Test
    public void zip1(){
        Observable<LocalDateTime> nextTenDays = Observable.range(1, 10)
                .map(i -> LocalDateTime.now().plusDays(i));

        Observable.just(City.Warsaw, City.London, City.Paris)
                .flatMap(city -> nextTenDays.map(day -> new Vacation(city, day)))
                .flatMap(vacation -> Observable.zip(
                    vacation.weather().filter(Weather::isSunny),
                    vacation.cheapFlightFrom(City.NewYork),
                    vacation.cheapHotel(),
                    (w, f, h) -> vacation
                )).subscribe(value -> log.info("ret value : {}", value)) ;

    }


    @Test
    public void zip2(){
        Observable<Long> red = Observable.interval(11, TimeUnit.MILLISECONDS);
        Observable<Long> green = Observable.interval(10, TimeUnit.MILLISECONDS);
        Observable.zip(
                red.timestamp(),
                green.timestamp(),
                (r, g) -> r.getTimestampMillis() - g.getTimestampMillis())
                .forEach(value -> log.info("value : {}", value));
        CommonUtil.sleepQuiet(10000);
    }



}
