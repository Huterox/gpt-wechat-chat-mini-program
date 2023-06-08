package com.huterox.whitehole.whiteholegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WhiteHoleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteHoleGatewayApplication.class, args);
    }

}
