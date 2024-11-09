package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.MyUser;
import com.example.demo.mapper.MyUserMapper;
import com.example.demo.service.MyUserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MybatisPlusController {


//    MybatisPlus框架数据交互
//    MyBatis-Plus（简称 MP）是一个 MyBatis的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
//    既能灵活掌控逻辑又能快速完成开发的持久层框架。
//    官方网站地址：https://baomidou.com

//    一、快速上手 --对应 MyUser MyUserMapper--
//    哈哈哈 污污污 步1.添加依赖
//    步2.然后依然是实体类，可以直接映射到数据库中的表。接着编写一个Mapper来操作了
//    步3.来写一个简单测试用例，可以看到这个Mapper提供的方法还是很丰富的

    @Resource
    MyUserMapper mapper;

    @Resource
    MyUserService service;

    @GetMapping("/my")
    @ResponseBody
    public String my(){
        System.out.println(mapper.selectById(1));  //同样可以直接selectById，非常快速方便
        MyUser myUser = new MyUser();
        myUser.setName("小明");
        myUser.setEmail("191@qq.com");
        myUser.setPassword("123456");
//        放到insert上可以看到返回int类型
        int insert = mapper.insert(myUser);
        System.out.println(insert);
        return "";
    }

//    二、条件构造器 --对应 MybatisConfiguration--
//    步4.对于一些复杂查询的情况，MybatisPlus支持我们自己构造QueryWrapper用于复杂条件查询
//    步5.在配置中开启SQL日志打印
    @GetMapping("/my1")
    @ResponseBody
    public String my1(){
//        通过使用QueryWrapper对象进行查询，也就等价于下面的SQL语句
//        select id,name,email,password from user where id >= 2 order by id desc
        QueryWrapper<MyUser> wrapper = new QueryWrapper<>();    //复杂查询可以使用QueryWrapper来完成
        wrapper
                .select("id", "name", "email", "password")    //可以自定义选择哪些字段
                .ge("id", 2)     			//选择判断id大于等于1的所有数据
                .orderByDesc("id");   //根据id字段进行降序排序
        System.out.println(mapper.selectList(wrapper));   //Mapper同样支持使用QueryWrapper进行查询

        QueryWrapper<MyUser> wrapper0 = new QueryWrapper<>();
        wrapper0
                .eq("name", "小明");
        System.out.println(mapper.selectOne(wrapper0));

//        Wrappers 等同于 QueryWrapper<MyUser> wrapper = new QueryWrapper<>();
        QueryWrapper<MyUser> wrapper1 = Wrappers
                .<MyUser>query()
                .gt("id", 1)
                .orderByDesc("id");
        System.out.println(mapper.selectList(wrapper1));

        QueryWrapper<MyUser> wrapper2 = Wrappers
                .<MyUser>query()
                .eq("id", 2)
                .like("name", "花");
//                .and(w -> w.like("name", "es"));
//        SELECT id,name,email,password FROM myuser WHERE (id = ? AND (name LIKE ?))
        System.out.println(mapper.selectList(wrapper2));
        return "";
    }

//    步6.也可以直接使用批处理操作
    @Transactional
    @GetMapping("/my2")
    @ResponseBody
    public String my2(){
//        使用数据库最忌讳for循环一条一条执行
//        mapper.deleteById()
        //支持批处理操作，我们可以一次性删除多个指定ID的用户
        int count = mapper.deleteBatchIds(List.of(1, 2));
        System.out.println(count);
        return "";
    }

//    步7.可以快速进行分页查询操作，需要配置一下MybatisConfiguration，这样就可以使用分页功能了
//    步8.对于数据更新操作，也可以使用UpdateWrapper非常方便的来完成，这样就可以快速完成更新操作了

    @GetMapping("/my3")
    @ResponseBody
    public String my3(){
//        步7.
//        当前第几页，每页几条数据, Wrappers.emptyWrapper()
        //这里我们将用户表分2页，并获取第一页的数据
        Page<MyUser> myUserPage = mapper.selectPage(Page.of(1, 2), Wrappers.query());
        Page<MyUser> myUserPage1 = mapper.selectPage(Page.of(1, 2),
                Wrappers.<MyUser>query().orderByDesc("id"));
        System.out.println(myUserPage.getRecords());   //获取分页之后的数据
//        步8.
        UpdateWrapper<MyUser> wrapper = new UpdateWrapper<>();
        wrapper
                .set("name", "lbw")
                .eq("id", 1);
        System.out.println(mapper.update(null, wrapper));

//        全选直接拉拽到下面
//        Wrappers.<MyUser>update().set("name", "lbw").eq("id", 1);
        mapper.update(null, Wrappers.<MyUser>update().set("name", "lbw").eq("id", 1));

//        QueryWrapper和UpdateWrapper还有专门支持Java 8新增的Lambda表达式的特殊实现
//        LambdaQueryWrapper<MyUser> wrapper1 = Wrappers
//                .<MyUser>lambdaQuery()
//                .eq(MyUser::getId, 2)   //比如我们需要选择id为2的用户，前面传入方法引用，后面比的值
//                .select(MyUser::getName, MyUser::getId);   //比如我们只需要选择name和id，那就传入对应的get方法引用
//        System.out.println(mapper.selectOne(wrapper1));
        return "";
    }

//    接口基本操作

//    把Service也给弄个模版 MybatisPlus为我们提供了很方便的CRUD接口，直接实现了各种业务中会用到的增删改查操作。
    @GetMapping("/my4")
    @ResponseBody
    public String my4(){
        MyUser id = service
                .query()
                .eq("id", 1)
                .one();
        System.out.println(id);

////        批量插入一组用户数据到数据库中
//        List<MyUser> users = List.of(new MyUser("xxx"), new MyUser("yyy"));
//        //预设方法中已经支持批量保存了，这相比我们直接用for效率高不少
//        service.saveBatch(users);
////        还有更加方便快捷的保存或更新操作，当数据不存在时（通过主键ID判断）则插入新数据，否则就更新数据
//        service.saveOrUpdate(new MyUser("aaa"));
////        也可以直接使用Service来进行链式查询
//        System.out.println(service.query().list());
//        MyUser one = service.query().eq("id", 1).one();
//        System.out.println(one);
        return "";
    }

}
