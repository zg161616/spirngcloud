package com.example.controller;

import entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.StudentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bwh
 * @date 2020/1/2/002 - 15:08
 * @Description
 */
@RestController
public class StudentController {
    @Resource
    StudentService studentService;

    @RequestMapping("/get")
    public String get(@RequestParam(value="id",required=false)int id){
        return studentService.info(id);
    }

    @GetMapping("/student")
    public Student postStudent(@RequestBody(required=false) Student student){
        return student;
    }

    @PostMapping("/findAll")
    public List<Student> findAll(){
        return studentService.findAll();
    }
}
