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
