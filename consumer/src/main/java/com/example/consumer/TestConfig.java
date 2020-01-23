package com.example.consumer;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bwh
 * @date 2020/1/6/006 - 15:30
 * @Description
 */
@Configuration
@ExcludeFromComponentScan
public class TestConfig {

    @Bean
    public IRule ribbonRule( ) {
        return new RandomRule();
    }
}
