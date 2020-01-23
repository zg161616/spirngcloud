package com.example.consumer.feign;

import entity.Student;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bwh
 * @date 2020/1/18/018 - 10:00
 * @Description
 */
@Component
public class HystrixFactory implements FallbackFactory<DemoFeignFactory> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixFactory.class);

    @Override
    public DemoFeignFactory create(Throwable throwable) {
        HystrixFactory.LOGGER.info("messageg is :"+throwable.getMessage());
        return new DemoFeignFactory() {
            @Override
            public Student json(Student s) {
                Student student =new Student();
                student.setName("factory_hystrix");
                return student;
            }

            @Override
            public List<Student> findAll() {
                Student student =new Student();
                student.setName("factory_hystrix");
                ArrayList<Student> students = new ArrayList<>();
                students.add(student);
                return students;
            }
        };
    }


}
