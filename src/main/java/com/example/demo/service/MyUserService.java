package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.MyUser;
import org.springframework.stereotype.Service;

@Service
public interface MyUserService extends IService<MyUser> {
//    除了继承模版，我们也可以把它当成普通Service添加自己需要的方法
    void test();
}
