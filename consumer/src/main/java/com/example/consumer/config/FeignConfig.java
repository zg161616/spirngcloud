package com.example.consumer.config;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author bwh
 * @date 2020/1/6/006 - 11:45
 * @Description
 */
@Configuration
public class FeignConfig {
    @Bean
    public Contract feginContract(){
        return new Contract.Default();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    //单独禁用hystrix
//    @Bean
//    @Scope("prototype")
//    public Feign.Builder feignBuilder() {
//        return Feign.builder();
//    }
}
