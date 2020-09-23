package cn.lacknb.springbootspringdatajpastudy;

import cn.lacknb.springbootspringdatajpastudy.pojo.Student;
import cn.lacknb.springbootspringdatajpastudy.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootSpringDataJpaStudyApplicationTests {

    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
    }


    @Test
    public void add () {
        Student student = new Student();
        student.setSName("张三");
        student.setSSex("男");
        student.setSBirth("1999-09-02");
        Student student1 = studentService.addStudent(student);
        System.out.println(student1);
    }

    @Test
    public void update () {
        Student student = new Student();
        student.setSId(2);
        student.setSName("李四");
        student.setSSex("女");
        student.setSBirth("1991-09-02");
        int row = studentService.updateStudent(student);
        System.out.println(row);  // 1
    }

    @Test
    public void select () {
        Student student = studentService.selectById(0);
        System.out.println(student);
    }

    @Test
    public void delete () {
        int row = studentService.deleteStudent(0);
        System.out.println(row);
    }

    @Test
    public void selectByOther () {
        Student student = studentService.selectByOther("李四", "女");
        System.out.println(student);
//        List<Student> students = studentService.selectByOther("李四", "女");
//        students.forEach(System.out::println);
    }

    @Test
    public void selectAllStudent () {
        List<Student> students = studentService.selectAllStudent();
        students.forEach(System.out::println);
    }

}
