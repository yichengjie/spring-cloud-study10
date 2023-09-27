package com.yicj.openfeign.config;


import com.yicj.openfeign.client.HelloClientManual;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

@Import(FeignClientsConfiguration.class)
public class HelloClientManualConfiguration {

    @Autowired
    private Client client ;
    @Autowired
    private Decoder decoder ;
    @Autowired
    private Encoder encoder ;
    @Autowired
    private Contract contract ;

    public HelloClientManual buildHelloClientManual(){
        //String url = "http://127.0.0.1:8081" ;
        String url = "http://nacos-client-app" ;
        HelloClientManual target = Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(HelloClientManual.class, url);
        return target ;
    }
}
