package com.example.demo.repo;

import com.example.demo.entity.Jpa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository有两个泛型，前者是具体操作的对象实体，也就是对应的表，
//后者是ID的类型，接口中已经定义了比较常用的数据库操作。编写接口继承即可，我们可以直接注入此接口获得实现类
@Repository
public interface AccountRepository extends JpaRepository<Jpa, Integer> {
    //按照表中的规则进行名称拼接，不用刻意去记，IDEA会有提示
    List<Jpa> findAllByUsernameLike(String str);

    List<Jpa> findJpaByIdGreaterThanOrderByIdDesc(int i);
    //也可以使用Optional类进行包装，Optional<Account> findByIdAndUsername(int id, String username);
    Jpa findJpaByUsernameAndId(String username, int id);
    //使用exists判断是否存在
    boolean existsJpaById(int id);

//    步21.
//    JPQL自定义SQL语句
    @Transactional    //DML操作需要事务环境，可以不在这里声明，但是调用时一定要处于事务环境下
    @Modifying     //表示这是一个DML操作
    @Query("update Jpa set password = ?2 where id = ?1") //这里操作的是一个实体类对应的表，参数使用?代表，后面接第n个参数
    int updatePasswordById(int id, String newPassword);
//    步22.
//    编写原生SQL语句
    @Transactional
    @Modifying
    @Query(value = "update jpa set password = :password where username = :username", nativeQuery = true) //使用原生SQL，和Mybatis一样，这里使用 :名称 表示参数，当然也可以继续用上面那种方式。
//    @Param  引入第二个
    int updatePasswordByUsername(@Param("username") String newUsername,   //我们可以使用@Param指定名称
                                 @Param("password") String newPassword);

}
