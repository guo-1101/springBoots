package com.example.demo;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    JavaMailSender sender;   //JavaMailSender是专门用于发送邮件的对象，自动配置类已经提供了Bean

    @Test
    void contextLoads() {

//        步3.
        //SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("【电子科技大学教务处】关于近期学校对您的处分决定");
        //设置邮件内容
        message.setText("XXX同学您好，经监控和教务巡查发现，您近期存在旷课、迟到、早退、上课刷抖音行为，" +
                "现已通知相关辅导员，请手写5000字书面检讨，并在2023年4月1日17点前交到辅导员办公室。");
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo("1916093353@qq.com");
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("DoWhatYouCant@163.com");
        //OK，万事俱备只欠发送
//        测试类每次启动都运行
//        sender.send(message);
    }

    @Test
    void contextLoadss() throws MessagingException {

//        步4.
        //创建一个MimeMessage
        MimeMessage message = sender.createMimeMessage();
        //使用MimeMessageHelper来帮我们修改MimeMessage中的信息
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("Test");
        helper.setText("lbwnb");
        helper.setTo("你的QQ号@qq.com");
        helper.setFrom("javastudy111@163.com");
        //发送修改好的MimeMessage
//        sender.send(message);
    }

}
