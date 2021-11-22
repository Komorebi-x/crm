package com.xx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName Starter
 * @Description TODO
 * @Author xu
 * @Date 2021/10/22 10:48
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.xx.crm.dao")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
