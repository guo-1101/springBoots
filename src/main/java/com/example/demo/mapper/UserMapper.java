package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//表示这个接口是用于MyBatis的Mapper的接口
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findUserById(int id);

    @Insert("insert into user (username, email, password) values(#{username}, #{email}, #{password})")
    void createUser(String username, String email, String password);
}
