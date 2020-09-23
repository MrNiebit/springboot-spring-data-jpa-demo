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
