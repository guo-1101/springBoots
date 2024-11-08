package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "jpa_subject")   //学科信息表
public class JpaSubject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    @Id
    int sid;

    @Column(name = "name")
    String name;

    @Column(name = "teacher")
    String teacher;

    @Column(name = "time")
    int time;

//    步17.
    @ManyToOne
    @JoinColumn(name = "tid")   //存储教师ID的字段，和一对一是一样的，也会当前表中创个外键
    JpaTeacher jpaTeacher;

//    步19.
    @ManyToMany   //多对多场景
    @JoinTable(name = "jpa_teach_relation",     //多对多中间关联表
            joinColumns = @JoinColumn(name = "sid"),    //当前实体主键在关联表中的字段名称
            inverseJoinColumns = @JoinColumn(name = "tid")   //教师实体主键在关联表中的字段名称
    )
    List<JpaTeacher> jpaTeacherList;
}
