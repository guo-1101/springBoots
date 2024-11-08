package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jpa_score")   //成绩表，注意只存成绩，不存学科信息，学科信息id做外键
public class JpaScore {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    int id;

    @OneToOne   //一对一对应到学科上
    @JoinColumn(name = "sid")
    JpaSubject jpaSubject;

    @Column(name = "socre")
    double score;

    @Column(name = "jid")
    int jid;
}
