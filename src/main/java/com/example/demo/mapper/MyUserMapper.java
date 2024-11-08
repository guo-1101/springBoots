package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {
    //使用方式与JPA极其相似，同样是继承一个基础的模版Mapper
    //这个模版里面提供了预设的大量方法直接使用，跟JPA如出一辙

//    mybatis之前的还可以用
    @Select("")
    int se();

}
