package com.example.consumer.feign;

import com.example.consumer.config.FeignConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;


/**
 * @author bwh
 * @date 2020/1/6/006 - 14:02
 * @Description
 */
@Component
public class FeignServiceFallback2 implements  FeignService2{

    @Override
    public String findServiceFromEureka(String serviceName) {
        return "haha";
    }
}
