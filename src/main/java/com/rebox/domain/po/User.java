package com.rebox.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    /**
     * 主键，自动增长，用户ID
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
     * 账号过期时间
     */
    private Date expireTime;

    /**
     * 账号状态（0正常，1锁定，2禁用）
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 一对一关联了一个创建人的用户对象
     */
    private User createByPO;

    /**
     * 一对一关联了一个编辑人的用户对象
     */
    private User editByPO;

    private static final long serialVersionUID = 1L;
}