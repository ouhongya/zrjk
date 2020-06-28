package com.zrjk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(value = "com.zrjk.MonsterZrjk.MainForce.*.*.Mapper")
@ComponentScan(basePackages = {"com.zrjk.*"})
public class ZrjkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZrjkApplication.class,args);
    }
}
