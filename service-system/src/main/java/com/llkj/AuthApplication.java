package com.llkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName AuthApplication
 * @Description TODO
 * @Author qing
 * @Date 2022/12/14 17:07
 * @Version 1.0
 */

@SpringBootApplication
@MapperScan("com.llkj.system.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AuthApplication.class, args);
//        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
    }
}
