package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.MyUser;
import com.example.demo.mapper.MyUserMapper;
import com.example.demo.service.MyUserService;
import org.springframework.stereotype.Service;

//实现类 实现MyUserService
//需要继承ServiceImpl才能实现那些默认的CRUD方法
@Service
public class MyUserServiceImpl extends ServiceImpl<MyUserMapper, MyUser> implements MyUserService {
    @Override
    public void test() {

    }
}
