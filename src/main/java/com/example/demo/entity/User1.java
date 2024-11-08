package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
//步6.
//Swagger：查看实体类简介以及各个属性的介绍
@Schema(description = "用户信息实体类")
public class User1 {
    @Schema(description = "用户名称")
//    步4.
//    校验注解
    @Length(min = 3)
    String username;
    @Length(min = 10)
    String password;
}
