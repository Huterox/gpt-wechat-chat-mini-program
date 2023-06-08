package com.huterox.ikun.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.huterox.ikun.chat.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class IkunChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(IkunChatApplication.class, args);
    }

}
