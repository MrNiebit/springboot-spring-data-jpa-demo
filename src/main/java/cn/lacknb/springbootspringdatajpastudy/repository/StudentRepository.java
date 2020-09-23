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
