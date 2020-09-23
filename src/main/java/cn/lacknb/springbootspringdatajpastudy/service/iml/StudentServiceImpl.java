package cn.lacknb.springbootspringdatajpastudy.service.iml;

import cn.lacknb.springbootspringdatajpastudy.pojo.Student;
import cn.lacknb.springbootspringdatajpastudy.repository.StudentRepository;
import cn.lacknb.springbootspringdatajpastudy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author gitsilence
 * @version 1.0
 * @date 2020/9/22 0022 下午 15:59
 * @mail gitsilence@lacknb.cn
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * SpringDataJPA 做删改的时候,是需要有事务参与的。忘记加事务就会报这种错误
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteStudent(int id) {
        try {
            studentRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateStudent(Student student) {
        try {
            System.out.println(student);
            return studentRepository.updateStudentById(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Student selectById(int id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student selectByOther(String sName, String sSex) {
        return studentRepository.selectByOther(sName, sSex);
    }

    @Override
    public List<Student> selectAllStudent() {
        return studentRepository.findAll();
    }
}
