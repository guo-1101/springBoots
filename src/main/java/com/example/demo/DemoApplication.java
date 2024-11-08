package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class DemoApplication {

//    args可以理解成yml配置文件参数
    public static void main(String[] args) {
//        SpringApplication中的静态run方法
        SpringApplication.run(DemoApplication.class, args);
//        传的启动类（DemoApplication.class）封装到数组里了
//        把当前的类（SpringApplication）new了一个实例化对象，调用这个对象的run方法

//        @ComponentScan 配置包扫描的 默认扫描当前类所在的路径下的所有（DemoApplication com.example.demo） 自动扫描

    }

}

//修改主类，不使用内置Tomcat服务器启动
//@SpringBootApplication
//public class DemoApplication extends SpringBootServletInitializer {  //继承专用的初始化器
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//
//    //重写configure方法，完成启动类配置
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(DemoApplication.class);
//    }
//}