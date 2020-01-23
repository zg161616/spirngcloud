package com.example.consumer.feign;

import com.example.consumer.config.FeignConfig;
import com.netflix.hystrix.Hystrix;
import entity.Student;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author bwh
 * @date 2020/1/6/006 - 9:31
 * @Description
 */
@FeignClient(name="provider",configuration = FeignConfig.class,fallbackFactory = HystrixFactory.class)
public interface DemoFeignService {
     @RequestLine("GET /student")
     Student json(@RequestBody  Student s);

     @RequestLine("POST /findAll")
     List<Student> findAll();


}


