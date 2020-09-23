# Spring Data Jpa 增删改查



一些坑，都在注释。



## 导入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.21</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
            <scope>provided</scope>
        </dependency>

```



## 实体类

```java
package cn.lacknb.springbootspringdatajpastudy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * @author gitsilence
 * @version 1.0
 * @date 2020/9/22 0022 下午 15:48
 * @mail gitsilence@lacknb.cn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_jpa")
public class Student implements Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "s_id")
    private Integer sId;

    @Column(name = "s_name")
    private String sName;

    @Column(name = "s_birth")
    private String sBirth;

    @Column(name = "s_sex")
    private String sSex;

    @Override
    public Object getId() {
        // 返回主键
        return sId;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}

```



## Dao层

```java
package cn.lacknb.springbootspringdatajpastudy.repository;

import cn.lacknb.springbootspringdatajpastudy.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gitsilence
 *  JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 *  * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 * @version 1.0
 * @date 2020/9/22 0022 下午 15:57
 * @mail gitsilence@lacknb.cn
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    /*
    *    一JPQL和SQL
            1.JPQL和SQL很像,查询关键字都是一样的
            2.唯一的区别是:JPQL是面向对象的
        二、JPQL书写规则
            JPA的查询语言,类似于sql

            1.里面不能出现表名,列名,只能出现java的类名,属性名，区分大小写
            2.出现的sql关键字是一样的意思,关键字不区分大小写
            3.不能写select * 要写select 别名
           JPQL不支持insert操作
    * */


    /*
    *
    * 有nativeQuery = true时，是可以执行原生sql语句，所谓原生sql，也就是说这段sql拷贝到数据库中，然后把参数值给一下就能运行了
    * 没有nativeQuery = true时，就不是原生sql，而其中的select * from xxx中xxx也不是数据库对应的真正的表名，而是对应的实体名，
    * 并且sql中的字段名也不是数据库中真正的字段名，而是实体的字段名
    * */

    /**
     * nativeQuery = true条件下
     * @param student
     * @return
     */
//    @Modifying
//    @Query(value = "update Student as s set s.sName = :#{#student.sName}, s.sBirth = :#{#student.sBirth}, s.sSex = :#{#student.sSex} where s.sId = :#{#student.sId}", nativeQuery = false)
//    int updateStudentById (Student student);

    /**
     * naticeQuery = false 条件下
     * @param student
     * @return
     */
    @Modifying
    @Query(value = "update student_jpa set s_name = :#{#student.sName}, s_birth = :#{#student.sBirth}, s_sex = :#{#student.sSex} where s_id = :#{#student.sId}", nativeQuery = true)
    int updateStudentById (Student student);


    // 这种方法虽然可以，当数据过多的时候，效率就会很低
//    @Query(value = "select new cn.lacknb.springbootspringdatajpastudy.pojo.Student(s.sId, s.sName, s.sBirth, s.sSex) from Student as s where s.sName = ?1 and s.sSex = ?2", nativeQuery = false)
    /*
      * No converter found capable of converting from type [java.lang.Integer] to type
        JPA 原生 sql 的返回不能直接转换成对象，原生 sql 的返回形式是 Map 、 List<Map> 或 Object，因此可以使用 List<Map<String,Object>> 接收
        Student selectByOther (String name, String sex);
    * */
//    List<Student> selectByOther (String name, String sex);


    /*
    * 使用 原生sql, 可以直接封装成Student。 这个方便
    * */
    @Query(value = "select s_id, s_name, s_sex, s_birth from student_jpa where s_name = :name and s_sex = :sex", nativeQuery = true)
//    List<Student> selectByOther (@Param("name") String name, @Param("sex") String sex);
    Student selectByOther (@Param("name") String name, @Param("sex") String sex);



    /*
    *   （1）可以通过自定义的 JPQL 完成 UPDATE 和 DELETE 操作。 注意： JPQL 不支持使用 INSERT；
        （2）在 @Query 注解中编写 JPQL 语句， 但必须使用 @Modifying 进行修饰. 以通知 SpringData， 这是一个 UPDATE 或 DELETE 操作
        （3）UPDATE 或 DELETE 操作需要使用事务，此时需要定义 Service 层，在 Service 层的方法上添加事务操作；
        （4）默认情况下， SpringData 的每个方法上有事务， 但都是一个只读事务。 他们不能完成修改操作。
    * */

}

```



## 业务层

```java
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

```



## application.yml

```java
spring:
  application:
    name: spring-data-jpa-study


  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql:///db01?useSSL=false&characterEncoding=utf8&serverTimezone=UTC
    username: root
    password: 123456



  jpa:
    generate-ddl: true
    show-sql: true
    database: mysql
    # 格式化sql
    properties:
      hibernate:
        format_sql: true

# 显示sql语句 绑定的参数
logging:
  level:
    org:
      hibernate:
        type:
          descriptor:
            sql:
              BasicBinder: trace

```



## 主启动类

```java
package cn.lacknb.springbootspringdatajpastudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("cn.lacknb.springbootspringdatajpastudy.repository")  // 开启JPA，需要指定包  和mybatis中的mapperScan类似
public class SpringbootSpringDataJpaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSpringDataJpaStudyApplication.class, args);
    }

}

```

