package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jpa_teacher")
public class JpaTeacher {

    @Column(name = "tid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int tid;

    @Column(name = "name")
    String name;

    @Column(name = "sex")
    String sex;
}
