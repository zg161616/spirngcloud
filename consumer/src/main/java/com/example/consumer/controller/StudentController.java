package com.example.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.consumer.feign.DemoFeignService;
import com.example.consumer.feign.FeignService2;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    DemoFeignService demoFeignService;

    @Autowired
    FeignService2 feignService2;

    @Reference(version = "1.0.0")
    private StudentService studentService;
    @Autowired
    RestTemplate restTemplate;



    @RequestMapping("/get")
    public String get(@RequestParam(value="id",required =false)int id){
            return studentService.info(id);
    }

    @RequestMapping("/info")
    @HystrixCommand(fallbackMethod = "jsonFallBack",commandProperties = @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"))
    public String info(@RequestParam(value="id",required=false)int id){
        String s = restTemplate.getForObject("http://provider/get?id="+id, String.class);
        return s;
    }


        @GetMapping("/json")
    @HystrixCommand(fallbackMethod = "jsonFallBack")
    public Student json(Student student){
        return demoFeignService.json(student);
    }

    public String jsonFallBack(int student){
        return "zzz";

    }
    public Student jsonFallBack(Student student){
        return new Student();

    }


    @GetMapping("/test")
    public String test(){
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("provider");
        return serviceInstance.getHost()+":"+serviceInstance.getPort();
    }

    @GetMapping("/findAll")
    public List<Student> findAll(){
        return demoFeignService.findAll();
    }



    @GetMapping("/{serviceName}")
    public String eurekaService(@PathVariable(name="serviceName") String serviceName){
        return feignService2.findServiceFromEureka(serviceName);
    }
}