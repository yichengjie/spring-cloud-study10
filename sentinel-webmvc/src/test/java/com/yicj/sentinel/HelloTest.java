package com.yicj.sentinel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;

import java.util.List;


@SpringBootTest(classes = SentinelApplication.class)
public class HelloTest {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters; ;

    @Test
    public void hello(){

        List<HttpMessageConverter<?>> converters = messageConverters.getObject().getConverters();
//        HttpMessageConverterExtractor<?> extractor = new HttpMessageConverterExtractor(type, converters);
//        extractor.extractData(new FeignResponseAdapter(response));
    }
}
