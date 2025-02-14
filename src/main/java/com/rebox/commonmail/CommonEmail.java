package com.rebox.commonmail;

import com.rebox.ToEmail;
import com.rebox.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author 林瑶
 * @Description
 * @Date 2024/3/8 21:38
 */
@Component
public class CommonEmail {

    //接收者
    @Resource
    private JavaMailSender mailSender;

    //发送者
    @Value("${spring.mail.username}")
    private String from;


    public RestResult commonEmail(ToEmail toEmail) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(toEmail.getTos());
        //邮件标题
        message.setSubject(toEmail.getSubject());
        //邮件内容
        message.setText(toEmail.getContent());
        try {
            mailSender.send(message);
//            return JsonReturn.buildSuccess(toEmail.getTos(), "发送普通邮件成功");
            return RestResult.SUCCESS(toEmail.getTos() +"发送邮件成功");
        } catch (MailException e) {
            e.printStackTrace();
            return RestResult.FAIL("!普通邮件方失败");
        }
    }
}