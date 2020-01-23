package com.example.consumer.feign;

import entity.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bwh
 * @date 2020/1/15/015 - 14:40
 * @Description
 */
@Component
public class HystrixFallBack implements  DemoFeignService {
    @Override
    public Student json(Student s) {
        return new Student();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        return students;
    }
}
