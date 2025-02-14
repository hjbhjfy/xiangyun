package com.rebox.domain.query;

import com.rebox.Base;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class UserQuery extends Base {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 角色
     */
    private Integer role;
    /**
     * 账号过期时间
     * //当你是formData提交时，后端接收到的日期字符串，要转成Date类型的话，就需要使用@DateTimeFormat这个注解
     * //当你是json提交时，后端接收到的日期字符串，要转成Date类型的话，就需要使用@JsonFormat这个注解
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 账号状态（0正常，1锁定，2禁用）
     */
    private Integer state;

    public void setToken(String token) {
    }
}
