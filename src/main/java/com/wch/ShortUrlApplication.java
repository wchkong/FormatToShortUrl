package com.wch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wch.mapper")
public class ShortUrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class);
    }
}
