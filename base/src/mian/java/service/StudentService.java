package service;


import entity.Student;

import java.util.List;

/**
 * 学生信息Service接口
 * @author Administrator
 *
 */
public interface StudentService {

	public String info(int id);

	public List<Student> findAll();
}

