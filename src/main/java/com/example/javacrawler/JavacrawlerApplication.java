package com.example.javacrawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling   //开启定时
@MapperScan("com.example.javacrawler.mapper")
@EnableTransactionManagement    //开启事务
//@ComponentScan(basePackages = {"com.example.*"})
@SpringBootApplication
public class JavacrawlerApplication{
    static {
//        System.setProperty("selenuim_config", "E:\\作业\\软工综合课设\\代码\\src\\main\\resources");
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\IdeaProjects\\javacrawler\\src\\main\\resources\\config.ini");
    }

    public static void main(String[] args) {
        SpringApplication.run(JavacrawlerApplication.class, args);
    }

}
