package com.yicj.study.mvc.client;


import com.yicj.study.mvc.WebMvcApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
@SpringBootTest(classes = WebMvcApplication.class)
public class HttpMessageConverterExtractorTest {

    @Autowired
    private ObjectFactory<HttpMessageConverters> objectFactory;


    private ClientHttpResponse initResponse() throws IOException {
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://www.baidu.com") ;
        ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        ClientHttpRequest request = requestFactory.createRequest(uriBuilder.build(), HttpMethod.GET);
        return request.execute();
    }

    @Test
    public void extractData() throws IOException {
        ClientHttpResponse response = this.initResponse();
        Type type = String.class ;
        HttpMessageConverters converters = objectFactory.getObject();
        HttpMessageConverterExtractor<String> extractor = new HttpMessageConverterExtractor<>(type, converters.getConverters());
        String value = extractor.extractData(response);
        response.close();
        log.info("value : {}", value);
    }

    @Test
    public void hello(){
        UriBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://www.baidu.com") ;
        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.GET,uriBuilder.build()) ;
        RestTemplate restTemplate = new RestTemplate() ;
        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
        String value = exchange.getBody();
        log.info("value : {}", value);
    }

}
