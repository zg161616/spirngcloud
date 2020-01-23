package dao;

import entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 学生信息Dao接口
 * @author Administrator
 *
 */

public interface StudentDao extends JpaRepository<Student,Integer>{

}
