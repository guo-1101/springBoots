package com.example.demo.controller;

import com.example.demo.entity.User1;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.logging.Logger;

//步4.
//使用@Tag注解来添加Controller描述信息
@Tag(name = "TestController账户验证相关", description = "包括用户登录、注册、验证码请求等操作。")

//步2.
//开启接口校验功能
@Validated   //首先在Controller上开启接口校验

@Slf4j
@Controller
//@RestController
public class TestController {

//    步9.
//    添加自定义的配置
    @Value("${test.hello}")
    String hello;   //直接从配置中去取

    @Resource
    UserMapper userMapper;

    @Resource
    JavaMailSender sender;

    //    不需要在controller中写任何内容，Thymeleaf默认会将index.html作为首页文件
//    步3.
    @ResponseBody
    @GetMapping("/index")
    public String index(){
        System.out.println(hello);
        return "Hello World";
    }
//    步4.
//    一个对象现在也可以直接以JSON形式返回给客户端，无需任何配置
    @ResponseBody
    @GetMapping("/indexs")
    public User indexs(){
//        @AllArgsConstructor 相当于
//        User user = new User();
//        user.setId(1);
//        user.setUsername("小明");
//        user.setEmail("男");
//        user.setPassword("123456");
        return new User(1,"小明","191@qq.com","123456");
    }
//    步10.
    @ResponseBody
    @GetMapping("/test")
    public User test(){
        return userMapper.findUserById(1);
    }

//    二、打印日志信息
    @ResponseBody
    @GetMapping("/hello")
    public void hello(){
//        原生JUL日志实现
        Logger loggers = Logger.getLogger("test");
        loggers.info("Hello World");
//        SpringBoot打印日志信息
        org.slf4j.Logger logger = LoggerFactory.getLogger(TestController.class);
//        org.slf4j.Logger logger = LoggerFactory.getLogger("test");
        logger.info("我是SLF4J日志信息");
//        因为使用了Lombok，所以直接一个注解也可以搞定
        log.info("我是SLF4J日志信息Lombok");
//        日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，SpringBoot默认只会打印INFO以上级别的信息
    }
//    三、配置Logback日志
    @ResponseBody
    @GetMapping("/hellos")
    public String hellos(HttpServletRequest request){
//    public String hellos(HttpSession session){
//        比如需要记录是哪个用户访问我们网站的日志，官方提供的字段无法实现此功能，这时就需要使用MDC机制。
//        通过这种方式，我们就可以向日志中传入自定义参数了，日志中需要添加占位符%X{键值}：%clr([%X{reqId}]){faint}，名字保持一致。
//        这样当我们向MDC中添加信息后，只要是当前线程（本质是ThreadLocal实现）下输出的日志，都会自动替换占位符。
        MDC.put("reqID", request.getSession().getId());
        MDC.put("iP", request.getRemoteAddr());
        log.info("用户访问了一次测试接口");
        return "Hello World";
    }

//    一、实现邮件注册功能实战
    @PostMapping("/code")
    public String getCode(@RequestParam String email,
                          HttpSession session){
//        生成6位数验证码
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
//        把验证码和邮箱存到session
        session.setAttribute("code", code);
        session.setAttribute("email", email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("你的验证码");
        message.setText("验证码是："+code+"有效时间三分钟");
        message.setFrom("DoWhatYouCant@163.com");
        message.setTo("1916093353@qq.com");
        sender.send(message);
        return "发送成功";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam int code,
                           @RequestParam String password,
                           HttpSession session){
//        这种情况会报空错误
//        String sessionCode = session.getAttribute("code").toString();
        Integer sessionCode = (Integer) session.getAttribute("code");
//        String sessionEmail = session.getAttribute("email").toString();
        String sessionEmail = (String) session.getAttribute("email");
        if(sessionCode == null) return "请先获取验证码";
        if(sessionCode != code) return "验证码不正确";
//        判断是否获取完验证码又更换邮箱了
        if(!sessionEmail.equals(email)) return "请先获取验证码";
        userMapper.createUser(username, email, password);
        return "注册成功";
    }

//    步2.
    @ResponseBody
    @PostMapping("/submit")
    public String submit(@Length(min = 3) String username,  //使用@Length注解一步到位
                         @Length(min = 10) String password){
        System.out.println(username.substring(3));
        System.out.println(password.substring(2, 10));
        return "请求成功!";
    }
//    步4.
    @ResponseBody
    @PostMapping("/submits")
//    接口是以对象类型接收前端发送的表单数据的 在参数上添加@Valid注解表示需要验证
    public String submits(@Valid User1 user1){   //直接使用对象接收
        System.out.println(user1.getUsername().substring(3));
        System.out.println(user1.getPassword().substring(2, 10));
        return "请求成功!";
    }

//    步5.
//    不加@ResponseBody 或者 是@RestController 二选一 Swagger不显示类
//    不加@RequestParam Swagger不显示属性

//    对于那些不需要展示在文档中的接口，我们也可以将其忽略掉
//    @Hidden
//    表示响应
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "测试成功"),
            @ApiResponse(responseCode = "500", description = "测试失败")   //不同返回状态码描述
    })
//    接口功能描述
    @Operation(summary = "请求用户数据测试接口", description = "用户登录接口")
    @ResponseBody
    @PostMapping("/swagger")
//    example表示默认值
    public String swagger(@Parameter(description = "用户名", example = "KFCvivo50") @RequestParam String username,
                         @RequestParam String password){
        System.out.println(username.substring(3));
        System.out.println(password.substring(2, 10));
        return "请求成功!";
    }

}
