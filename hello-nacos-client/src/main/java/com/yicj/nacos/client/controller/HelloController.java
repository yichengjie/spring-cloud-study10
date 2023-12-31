package com.yicj.nacos.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月18日 13:26
 */
@RestController
@RequestMapping("/hello")
public class HelloController {


    @Autowired
    private DiscoveryClient discoveryClient ;

    @Autowired
    private LoadBalancerClient loadBalancerClient ;


    @GetMapping("/index")
    public String index(){
        String serviceId = "nacos-client-app" ;
        //ServiceInstance choose = loadBalancerClient.choose(serviceId);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        return "hello nacos client index !" ;
    }

    @GetMapping("/exception")
    public String exception(){
        throw new RuntimeException("测试nacos client异常！") ;
    }


}
