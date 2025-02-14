package com.rebox.constant;

/**
 * ClassName:Constant
 * Package:com.rebox.constant
 * Descriptionn:
 *
 * @Author wqh
 * @Create 2024/5/8 20:52
 * @Version 1.0
 */
/**
 * 常量类
 *
 */
public class Constant {

    //用户账号状态：正常是58、锁定是59、禁用是60
    public static final int USER_STATE_LOCK = 59;

    public static final int USER_STATE_DISABLED = 60;

    /**
     * redis的key的命令规范：xxx:xxx:xxx 格式
     * 项目名:业务模块名:功能名:唯一参数值(比如userID)
     */
    public static final String REDIS_TOKEN_KEY = "crm:user:login:";

//    /**
//     * 字典数据的redis的key
//     *
//     */
//    public static final String REDIS_DIC_KEY = "crm:dic:dicdata";

    /**
     * token过期时间：7天
     */
    public static final long EXPIRE_TIME = 7*24*60;

    /**
     * token过期时间：30分钟，是默认值
     */
    public static final long DEFAUL_EXPIRE_TIME = 30;

    /**
     * 每页显示多少条数据
     */
    public static final int PAGE_SIZE = 10;

    /**
     * 1表示逻辑删除
     */
    public static final int DELETED = 1;
}

