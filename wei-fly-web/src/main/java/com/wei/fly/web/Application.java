package com.wei.fly.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan(basePackages="com.wei.fly")
@MapperScan(basePackages={"com.wei.fly.dao"})
public class Application  {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
