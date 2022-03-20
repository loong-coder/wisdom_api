package com.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.edu.mapper"})
@SpringBootApplication(scanBasePackages = {"com.edu"})
public class WisdomApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisdomApiApplication.class, args);
    }

}
