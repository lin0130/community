package lin.community.communtiy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "lin.community.communtiy.mapper")
public class CommuntiyApplication {

    public static void main(String[] args) {

        SpringApplication.run(CommuntiyApplication.class, args);
    }

}
