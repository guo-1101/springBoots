package com.example.demo.controller;

import com.example.demo.entity.Jpa;
import com.example.demo.entity.JpaDetail;
import com.example.demo.repo.AccountRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class JpaController {


//    JPA框架数据交互

//    实际上大部分的数据库交互操作，到最后都是把数据库中的数据映射为Java中的对象。通过调用Mapper中的方法就能直接获得实体类。
//    那么能否有一种框架，帮我们把这些相同的SQL语句给封装起来，直接把这类相似的SQL语句给屏蔽掉，不再由我们编写，而是让框架自己去组合拼接。

//    在之前，我们使用JDBC或是Mybatis来操作数据，通过直接编写对应的SQL语句来实现数据访问，但是我们发现实际上我们在Java中大部分操作
//    数据库的情况都是读取数据并封装为一个实体类，因此，为什么不直接将实体类直接对应到一个数据库表呢？也就是说，一张表里面有什么属性，
//    那么我们的对象就有什么属性，所有属性跟数据库里面的字段一一对应，而读取数据时，只需要读取一行的数据并封装为我们定义好的实体类既可以，
//    而具体的SQL语句执行，完全可以交给框架根据我们定义的映射关系去生成，不再由我们去编写，因为这些SQL实际上都是千篇一律的。

//    JPA（Java Persistence API）和JDBC类似，也是官方定义的一组接口，但是它相比传统的JDBC，它是为了实现ORM而生的，
//    即Object-Relationl Mapping，它的作用是在关系型数据库和对象之间形成一个映射，这样，我们在具体的操作数据库的时候，
//    就不需要再去和复杂的SQL语句打交道，只要像平时操作对象一样操作它就可以了。

//    比较常见的JPA实现：
//    Hibernate：Hibernate是JPA规范的一个具体实现，也是目前使用最广泛的JPA实现框架之一。它提供了强大的对象关系映射功能，
//    可以将Java对象映射到数据库表中，并提供了丰富的查询语言和缓存机制。

//    而实现JPA规范的框架一般最常用的就是Hibernate，而SpringDataJPA也是采用Hibernate框架作为底层实现，并对其加以封装。

//    官网：https://spring.io/projects/spring-data-jpa

//    一、使用JPA快速上手 --对应 Jpa AccountRepository--
//    步1.导入stater依赖
//    步2.直接创建一个类，比如用户类。接着通过注解形式，在属性上添加数据库映射关系，这样就能够让JPA知道我们的实体类对应的数据库表长啥样。
//    步3.接着我们来修改一下配置文件application-dev.yml，把日志打印给打开。
//    步4.在启动时日志中执行了SQL语句，数据库中对应的表已经自动创建好了。
//    步5.创建一个Repository实现类AccountRepository ，访问我们的表
//    步6.接口预置的方法。添加、查询操作。包括常见的一些计数、删除操作等都包含在里面，仅仅配置应该接口就能完美实现增删改查。

//    JPA依靠我们提供的注解信息自动完成了所有信息的映射和关联。相比Mybatis，JPA几乎就是一个全自动的ORM框架，而Mybatis则顶多算是半自动ORM框架。

    @Resource
    AccountRepository repository;

    @GetMapping("/jpa")
    @ResponseBody
    public String jpa(){
        Jpa jpa = new Jpa();
        jpa.setUsername("admin");
        jpa.setPassword("654321");
        System.out.println(repository.save(jpa));   //使用save来快速插入数据，并且会返回插入的对象，如果存在自增ID，对象的自增id属性会自动被赋值，这就很方便了
//        System.out.println(repository.save(jpa).getId());
        System.out.println(repository.count());

        //默认通过通过ID查找的方法，并且返回的结果是Optional包装的对象，非常人性化
        Optional<Jpa> byId = repository.findById(1);
//        ifPresent如果存在才打印
        repository.findById(1).ifPresent(System.out::println);
        return "";
    }

//    二、方法名称拼接自定义SQL --对应 AccountRepository--
//    需要进行条件查询等操作或是一些判断，就需要自定义一些方法来实现，通过方法名称的拼接来实现条件判断。

    @GetMapping("/jpa1")
    @ResponseBody
    public String jpa1(){
//        实现根据用户名模糊匹配查找用户
        repository.findAllByUsernameLike("%明%").forEach(System.out::println);

        System.out.println(repository.findJpaByIdGreaterThanOrderByIdDesc(0));
//        同时根据用户名和ID一起查询
        System.out.println(repository.findJpaByUsernameAndId("test", 1));
//        判断数据库中是否存在某个ID的用户
        System.out.println(repository.existsJpaById(2));
        return "";
    }

//    三、关联查询 --对应 Jpa JpaDetail JpaScore JpaSubject JpaTeacher--
//    在JPA中，每张表实际上就是一个实体类的映射，而表之间的关联关系，也可以看作对象之间的依赖关系

//    一对一关联
//    步7.比如用户表中包含了用户详细信息的ID字段作为外键，那么实际上就是用户表实体中包括了用户详细信息实体对象
//    步8.而用户信息和用户详细信息之间形成了一对一的关系，那么这时我们就可以直接在类中指定这种关系
//    步9.在修改实体类信息后，启动时表结构也进行了更新。
//    步10.直接进行查询，在建立关系之后，我们查询Jpa对象时，会自动将关联数据的结果也一并进行查询。
//    步11.只想要Jpa的数据，不想要用户详细信息数据，可以设置懒加载，这样只有在需要时才会向数据库获取。
//    步12.获取用户名之前，并没有去查询用户的详细信息，而是当我们获取详细信息时才进行查询并返回AccountDetail对象。
    @Transactional   //懒加载属性需要在事务环境下获取，因为repository方法调用完后Session会立即关闭
    @GetMapping("/jpa2")
    @ResponseBody
    public String jpa2(){
//        步10.
        repository.findById(1).ifPresent(System.out::println);
//        步12.
        Jpa jpa = repository.findById(1).get();
        System.out.println(jpa);
        System.out.println(jpa.getJpaDetail());
//        或者
        repository.findById(1).ifPresent(jpa1 -> {
            System.out.println(jpa1.getUsername());   //获取用户名
            System.out.println(jpa1.getJpaDetail());  //获取详细信息（懒加载）
        });
        return "";
    }

//    步13.在添加数据时，利用实体类之间的关联信息，一次性添加两张表的数据，需要修改一下级联关联操作设定
//    步14.可以多个并存，结束后会发现数据库中两张表都同时存在数据。
    @GetMapping("/jpa3")
    @ResponseBody
    public String jpa3(){
//        关联添加
        Jpa jpa = new Jpa();
        jpa.setUsername("Nike");
        jpa.setPassword("123456");
        JpaDetail detail = new JpaDetail();
        detail.setAddress("重庆市渝中区解放碑");
        detail.setPhone("1234567890");
        detail.setEmail("73281937@qq.com");
        detail.setRealName("张三");
        jpa.setJpaDetail(detail);
        jpa = repository.save(jpa);
        System.out.println("插入时，自动生成的主键ID为："+ jpa.getId()+"，外键ID为："+ jpa.getJpaDetail().getId());
//        关联删除
//        repository.deleteById(3);
        return "";
    }

//    一对多关联
//    步15.每个用户的成绩信息
//    步16.查询用户的成绩信息，成功得到用户所有的成绩信息，包括得分和学科信息。
    @GetMapping("/jpa4")
    @ResponseBody
    public String jpa4(){
        repository.findById(1).ifPresent(System.out::println);

        repository.findById(1).ifPresent(jpa -> {
            jpa.getJpaScoreList().forEach(System.out::println);
        });
        return "";
    }

//    多对一关联
//    步17.将对应成绩中的教师信息单独分出一张表存储，并建立多对一的关系，因为多门课程可能由同一个老师教授
//    步18.成功得到多对一的教师信息。

//    多对多关联
//    步19.我们一门课程可以由多个老师教授，而一个老师也可以教授多个课程，那么这种情况就是很明显的多对多场景，现在又该如何定义呢？
//    我们可以像之前一样，插入一张中间表表示教授关系，这个表中专门存储哪个老师教哪个科目。
//    步20.接着JPA会自动创建一张中间表，并自动设置外键，我们就可以将多对多关联信息编写在其中了。
    @GetMapping("/jpa5")
    @ResponseBody
    public String jpa5(){
//        步18.
        repository.findById(1).ifPresent(account -> {
            account.getJpaScoreList().forEach(score -> {
                System.out.println("课程名称："+score.getJpaSubject().getName());
                System.out.println("得分："+score.getScore());
                System.out.println("任课教师："+score.getJpaSubject().getJpaTeacher().getName());
            });
        });

//        步20.
        repository.findById(1).ifPresent(System.out::println);
        return "";
    }

//    四、JPQL自定义SQL语句 --对应 AccountRepository--
//    使用JPA，我们也可以像Mybatis那样，直接编写SQL语句，不过它是JPQL语言，与原生SQL语句很类似，但是它是面向对象的，
//    当然我们也可以编写原生SQL语句。

//    步21.更新用户表中指定ID用户的密码
//    步22.使用原生SQL来实现根据用户名称修改密码，通过编写原生SQL，在一定程度上弥补了SQL不可控的问题。
    @GetMapping("/jpa6")
    @ResponseBody
    public String jpa6(){
//        JPQL自定义SQL语句
        repository.updatePasswordById(1, "654321");
//        编写原生SQL语句
        repository.updatePasswordByUsername("小红", "654321");
        return "";
    }

}
