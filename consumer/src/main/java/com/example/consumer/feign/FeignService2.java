package com.example.consumer.feign;

import com.example.consumer.config.FeignConfig;
import entity.Student;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;


/**
 * @author bwh
 * @date 2020/1/6/006 - 14:02
 * @Description
 */
@FeignClient(name="feign2",url="http://localhost:8761",configuration = FeignConfig.class,fallback = FeignServiceFallback2.class)
public interface FeignService2 {
    @RequestLine("GET /eureka/apps/{serviceName}")
    String findServiceFromEureka(@Param("serviceName") String serviceName);

}
