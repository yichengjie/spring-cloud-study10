package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class UriComponentsBuilderTest {

    @Test
    public void hello() throws URISyntaxException {
        StringBuilder query = new StringBuilder() ;
        query.append("name=zhangsan") ;
        //
        URI uri = new URI("http://www.baidu.com?name=yicj&address=bjs") ;
        log.info("org query : {}", uri.getQuery());
        log.info("org query : {}", uri.getRawQuery());
        //

        URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri();
        // print new uri
        log.info("new uri: {}", newUri);
    }
}
