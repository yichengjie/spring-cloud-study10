package com.yicj.nacos;

import com.yicj.common.model.form.SavePersonForm;
import com.yicj.nacos.client.NacosClientApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

/**
 * @author yicj
 * @date 2023年09月07日 14:03
 */
@SpringBootTest(classes = NacosClientApplication.class)
public class HttpMessageConverterExtractorTest {

    @Autowired
    private HttpMessageConverters httpMessageConverters ;

    @Test
    public void hello() throws IOException {
//        ClientHttpResponse response = new MockClientHttpResponse() ;
//
//        RestTemplate restTemplate = new RestTemplate() ;
//        URI uri = UriComponentsBuilder.fromUriString("")
//                .build().toUri();
//
//        RequestEntity<SavePersonForm> requestEntity = new RequestEntity<>(HttpMethod.POST, ) ;
//
//
//        restTemplate.exchange()
//
//
//        Type type = String.class ;
//        List<HttpMessageConverter<?>> converters = httpMessageConverters.getConverters();
//        HttpMessageConverterExtractor<?> extractor = new HttpMessageConverterExtractor(type, converters);
//        extractor.extractData(response);
    }
}
