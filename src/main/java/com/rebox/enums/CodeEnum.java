package com.rebox.enums;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 枚举类
 *
 */
public enum CodeEnum {

    FIND_EMPLOYEE_INFO_ERROR(501,"查找员工信息表失败！"),

    SUCCESS(200, "成功"),

    FAIL(500, "失败"),



    LOGIN_ACCOUNT_EMPTY(101, "请输入登录账号"),

    LOGIN_PASSWORD_EMPTY(102, "请输入登录密码"),

    LOGIN_ACCOUNT_NONE_EXIST(103, "登录账号不存在"),

    LOGIN_PASSWORDD_ERROR(104, "登录密码不匹配"),

    LOGIN_ACCOUNT_EXPIRE(105, "登录账号已过期"),

    LOGIN_ACCOUNT_LOCK(106, "登录账号已锁定"),

    LOGIN_ACCOUNT_DISABLED(107, "登录账号已禁用"),

    REGISTER_ACCOUNT_EXIST(108, "注册账号已存在"),


    //--------------------------------------------------

    TOKEN_EMPTY(901, "请求Token为空"),

    TOKEN_SIGN_ILLEGAL(902, "请求Token签名验证不合法"),

    TOKEN_ERROR(903, "请求Token不正确"),

    //还可以继续写枚举.......，最后是分号结束
    ;

    //结果码（错误码）
    @Setter
    @Getter
    private int code;

    //结果信息（错误信息）
    @Setter
    @Getter
    private String msg;

    //枚举类的构造方法
    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}