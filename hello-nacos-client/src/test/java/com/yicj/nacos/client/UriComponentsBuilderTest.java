package com.yicj.nacos.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yicj
 * @date 2023年08月27日 17:13
 */
@Slf4j
public class UriComponentsBuilderTest {

    @Test
    public void hello() throws URISyntaxException {
        String originalUrl = "http://hello-user-server/user-service/hello/index" ;
        URI originalUri = new URI(originalUrl) ;
        //
        String scheme = "https" ;
        String host = "localhost" ;
        int port = 8085 ;
        boolean encoded = true ;
        URI newUri = UriComponentsBuilder.fromUri(originalUri)
                .scheme(scheme)
                .host(host)
                .port(port)
                .build(encoded)
                .toUri();
        log.info("new uri : {}", newUri.toASCIIString());
    }
}
