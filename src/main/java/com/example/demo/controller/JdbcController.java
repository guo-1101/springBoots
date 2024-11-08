package com.example.demo.controller;

import com.example.demo.entity.User;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class JdbcController {

//    步2.
    @Resource
    JdbcTemplate template;
//    步6.
    @Resource
    DataSource source;


//    JDBC数据交互框架 --对应 User--
//    除了一直认识的Mybatis之外，实际上Spring官方也提供了一个非常方便的JDBC操作工具，它同样可以快速进行增删改查。

//    步1.首先还是通过starter将依赖导入

//    JDBC模版类
//    Spring JDBC为我们提供了一个非常方便的JdbcTemplate类，它封装了常用的JDBC操作，我们可以快速使用这些方法来实现增删改查。

//    步2.我们要操作数据库，最简单直接的方法就是使用JdbcTemplate来完成，它给我们封装了很多方法使用。
//    步3.比如我们要查询数据库中的一条记录，可以使用queryForMap快速以Map为结果的形式查询一行数据。
//    步4.也可以编写自定义的Mapper用于直接得到查询结果
//    步5.当然除了这些之外，它还提供了update方法适用于各种情况的查询、更新、删除操作

//    JDBC简单封装

//    步6.对于一些插入操作，Spring JDBC为我们提供了更方便的SimpleJdbcInsert工具，它可以实现更多高级的插入功能
//    步7.比如我们的表主键采用的是自增ID，那么它支持插入后返回自动生成的ID
//    这样就可以快速进行插入操作并且返回自增主键了，还是挺方便的。

    @GetMapping("/jdbc")
    @ResponseBody
    public String jdbc(){
//        步3.
//        ("select * from user where id = 1")  输入 .var就弹出前面的了
        Map<String, Object> stringObjectMap = template.queryForMap("select * from user where id = 1");
        System.out.println(stringObjectMap);

        List<Map<String, Object>> maps = template.queryForList("select * from user");
        System.out.println(maps);
//        步4.
//        查询的数据映射到实体类，把数据库中的数据映射为Java中的对象
        User user = template.queryForObject("select * from user where id = ?",
                (r, i) -> new User(r.getInt(1), r.getString(2), r.getString(3), r.getString(4)), 2);
        System.out.println(user);
//        步5.
//        提供update方法适用于各种情况的查询、更新、删除操作
//        int update = template.update("insert into user values(3, '789', '789@qq.com', '123456789')");
//        System.out.println(update);
//        步7.
        //这个类需要自己创建对象
        SimpleJdbcInsert simple = new SimpleJdbcInsert(source)
                .withTableName("user")   //设置要操作的表名称
                .usingGeneratedKeyColumns("id");    //设置自增主键列
        Map<String, Object> users = new HashMap<>(2);  //插入操作需要传入一个Map作为数据
        users.put("username", "bob");
        users.put("email", "112233@qq.com");
        users.put("password", "123456");
        Number number = simple.executeAndReturnKey(users);   //最后得到的Numver就是得到的自增主键
        System.out.println(number);
        return "请求成功";
    }

}
