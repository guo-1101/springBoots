//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
////添加SpringSecurity的配置类
//@Configuration    //依然只需要Configuration注解即可，不需要其他配置
//public class SecurityConfiguration {
//    //配置方式跟之前SSM阶段是一样的
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> {

////                    放行login页面
////                    auth.requestMatchers("/static/**").permitAll();

////                    auth.anyRequest().authenticated();

//                    auth.anyRequest().permitAll();
//                })
//                .formLogin(conf -> {
//                    conf.loginPage("/login");
//                    conf.loginProcessingUrl("/doLogin");
//                    conf.defaultSuccessUrl("/");
//                    conf.permitAll();
//                })
//                .build();
//    }
//}
