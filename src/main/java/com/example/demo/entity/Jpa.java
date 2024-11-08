package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//通过注解形式，在属性上添加数据库映射关系，这样就能够让JPA知道我们的实体类对应的数据库表长啥样
@Data
@Entity    //表示这个类是一个实体类
@Table(name = "jpa")    //对应的数据库中表名称
public class Jpa {
    @Id    //此属性为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //生成策略，这里配置为自增
    @Column(name = "id")    //对应表中id这一列
//    使用int查询id为空时会报错
    Integer id;
    @Column(name = "username")    //对应表中username这一列
    String username;
    @Column(name = "password")    //对应表中password这一列
    String password;

//    一对一关联
//    步8.
    @JoinColumn(name = "detail_id")   //指定存储外键的字段名称
//    步11.
//    只想要Jpa的数据，不想要用户详细信息数据
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)    //将获取类型改为LAZY  设置懒加载
//    步13.
    @OneToOne(cascade = CascadeType.ALL)    //声明为一对一关系    //设置关联操作为ALL
    JpaDetail jpaDetail;
//    ALL：所有操作都进行关联操作
//    PERSIST：插入操作时才进行关联操作
//    REMOVE：删除操作时才进行关联操作
//    MERGE：修改操作时才进行关联操作

//    一对多关联
//    步15.
    @JoinColumn(name = "jid")  //注意这里的name指的是Score表中的uid字段对应的就是当前的主键，会将uid外键设置为当前的主键
    @OneToMany(cascade = CascadeType.REMOVE)   //在移除Account时，一并移除所有的成绩信息
    List<JpaScore> jpaScoreList;
}
