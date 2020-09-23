package cn.lacknb.springbootspringdatajpastudy.service;

import cn.lacknb.springbootspringdatajpastudy.pojo.Student;

import java.util.List;

/**
 * @author gitsilence
 * @version 1.0
 * @date 2020/9/22 0022 下午 15:59
 * @mail gitsilence@lacknb.cn
 */
public interface StudentService {

    Student addStudent (Student student);

    int deleteStudent (int id);

    int updateStudent (Student student);

    Student selectById (int id);

    Student selectByOther (String sName, String sSex);

    List<Student> selectAllStudent ();

}
