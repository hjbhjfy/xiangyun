package com.rebox.service.impl;

import com.rebox.constant.Constant;
import com.rebox.domain.po.User;
import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.dto.UserDTO;
import com.rebox.domain.dto.UserInfoDTO;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.RegisterQuery;
import com.rebox.domain.query.UserQuery;
import com.rebox.enums.CodeEnum;
import com.rebox.manager.JWTManager;
import com.rebox.manager.RedisManager;
import com.rebox.mapper.UserMapper;
import com.rebox.service.UserService;
import com.rebox.util.ExtBeanUtils;
import com.rebox.util.JSONUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.rebox.domain.dto.PageDTO;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j //lombok提供的日志注解
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper tUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JWTManager jwtManager;

    @Resource
    private RedisManager redisManager;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public LoginDTO register(RegisterQuery registerQuery) {
        Integer count = tUserMapper.countByAct(registerQuery.getLoginAct());
        if (count > 0) {
            return new LoginDTO(CodeEnum.REGISTER_ACCOUNT_EXIST);
        }
        String encode = passwordEncoder.encode(registerQuery.getLoginPwd());
        User user = new User();
        BeanUtils.copyProperties(registerQuery, user);
        user.setLoginPwd(encode);
        user.setState(0);
        user.setCreateTime(new Date());
        tUserMapper.insert(user);
        return new LoginDTO(CodeEnum.SUCCESS);
    }

    /**
     * 登录的业务实现
     *
     * @return
     */
    @Override
    public LoginDTO login(LoginQuery loginQuery) {
        //根据登录账号查询用户
        User tUser = tUserMapper.login(loginQuery.getLoginAct());
        System.out.println(tUser);
        if (ObjectUtils.isEmpty(tUser)) { //用户对象是空，说明用户不存在
            return new LoginDTO(CodeEnum.LOGIN_ACCOUNT_NONE_EXIST);
        }

        //判断密码是否匹配
        if (!passwordEncoder.matches(loginQuery.getLoginPwd(), tUser.getLoginPwd())) { //明文和密文不匹配的时候，那么久返回错误提示
            return new LoginDTO(CodeEnum.LOGIN_PASSWORDD_ERROR);
        }

//        if (!loginQuery.getLoginPwd().equalsIgnoreCase(tUser.getLoginPwd())) {
//            return new LoginDTO(CodeEnum.LOGIN_PASSWORDD_ERROR);
//        }
        //判断账号过期
//        if (tUser.getExpireTime().before(new Date())) {
//            //账号过期了
//            return new LoginDTO(CodeEnum.LOGIN_ACCOUNT_EXPIRE);
//        }

        //判断账号状态是否锁定
        if (tUser.getState() == Constant.USER_STATE_LOCK) {
            return new LoginDTO(CodeEnum.LOGIN_ACCOUNT_LOCK);
        }

        //判断账号状态是否禁用
        if (tUser.getState() == Constant.USER_STATE_DISABLED) {
            return new LoginDTO(CodeEnum.LOGIN_ACCOUNT_DISABLED);
        }

        //以上都验证通过了，那么久登录成功了
        UserDTO userDTO = new UserDTO();

        //Spring框架提供了一个对象属性复制的工具类：BeanUtils
        //把 tUser对象的所有属性值 复制到 userDTO对象属性里面去
        //复制时，需要两个对象的属性名相同、类型也相同才能复制
        BeanUtils.copyProperties(tUser, userDTO);

        //1、生成token
        //String token = "iuefhsudhfgjku"; // UUID.randomUUID().toString()
        //使用uuid生成一个不重复的字符串作为token是可以的，但是我们后续需要从token中获取一些业务参数数据，如果用uuid没法实现；
        //所以我们使用jwt生成一个token，因为jwt生成的token里面可以存放一些数据，并且这些数据可以解析出来；

        String userJSON = JSONUtils.writeValueAsString(userDTO); //把userDTO转成json字符串
        String token = jwtManager.createToken(userJSON);

        //2、token写入redis
        if (loginQuery.getIsRememberMe()) { //true, 选择了记住我
            redisManager.setValue(Constant.REDIS_TOKEN_KEY + token, token, Constant.EXPIRE_TIME, TimeUnit.MINUTES);
        } else {
            redisManager.setValue(Constant.REDIS_TOKEN_KEY + token, token, Constant.DEFAUL_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        //把token设置到返回对象中
        userDTO.setToken(token);

        //更新登录时间 (异步执行)
        /* 1、创建一个线程去执行
        new Thread(() -> {
            TUser user = new TUser();
            user.setId(tUser.getId());
            user.setLastLoginTime(new Date()); //用户最近登录时间
            tUserMapper.updateByPrimaryKeySelective(user);
        }).start();
        */

        //2、使用线程池异步执行
        threadPoolExecutor.execute(() -> {
            User user = new User();
            user.setId(tUser.getId());
            user.setLastLoginTime(new Date()); //用户最近登录时间
            int update = tUserMapper.updateByPrimaryKeySelective(user);
            log.info("更新用户id为 {} 的最近登录时间, 手机号为 {}, 更新结果为 {}.", tUser.getId(), tUser.getPhone(), update);
        });

        //登录返回
        return new LoginDTO(CodeEnum.SUCCESS, userDTO);
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Override
    public Boolean logout(String token) {
        return redisManager.removeKey(Constant.REDIS_TOKEN_KEY + token);
    }

    /**
     * 分页查询用户列表数据
     *
     * @param current
     * @return
     */
    @Override
    public PageDTO<UserInfoDTO> getUserByPage(Integer current) {
        //分页查询分两个查询，1、查符合条件的数据总条数；2、查询当前页的数据
        int totalRow = tUserMapper.selectByCount();

        // select * from t_user  limit start, pageSize
        int start = (current - 1) * Constant.PAGE_SIZE;
        List<User> userList = tUserMapper.selectByPage(start, Constant.PAGE_SIZE);

        int totalPage = totalRow / Constant.PAGE_SIZE;
        if (totalRow % Constant.PAGE_SIZE > 0) { //如果除不尽，总页数就需要加1
            totalPage ++;
        }

        //需要把 List<TUser> 转换为 List<UserInfoDTO>
        List<UserInfoDTO> userInfoDTOList = ExtBeanUtils.copyPropertiesForList(userList, UserInfoDTO.class);

        return new PageDTO<>(current, Constant.PAGE_SIZE, totalRow, totalPage, userInfoDTOList);
    }

    /**
     * 新增用户提交保存
     *
     * @param userQuery
     * @return
     */
    @Override
    public int saveUser(UserQuery userQuery) {
        User tUser = new User();

        BeanUtils.copyProperties(userQuery, tUser);

        //把密码加密
        String encodePwd = passwordEncoder.encode(userQuery.getLoginPwd());
        tUser.setLoginPwd(encodePwd); //密码要加密后存入数据库，不能存明文

        tUser.setCreateTime(new Date()); //创建时间

        //拿到当前登录人的id
        String token = userQuery.getToken();
        //通过token解析出里面的登录id数据
        Integer currentLoginUserId = jwtManager.getLoginUserId(token);

        tUser.setCreateBy(currentLoginUserId); //创建人
        return tUserMapper.insertSelective(tUser);
    }

    /***
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserInfoDTO getUserById(Integer id) {

        User tUser = tUserMapper.selectById(id);
        System.out.println(tUser);
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        BeanUtils.copyProperties(tUser, userInfoDTO);

        //兼容一下创建人和编辑人是空的情况，避免页面出现null的错误
        if (ObjectUtils.isEmpty(userInfoDTO.getCreateByPO())) {
            userInfoDTO.setCreateByPO(new User());
        }
        if (ObjectUtils.isEmpty(userInfoDTO.getEditByPO())) {
            userInfoDTO.setEditByPO(new User());
        }
        return userInfoDTO;
    }

    @Override
    public int updateUser(UserQuery userQuery) {
        User tUser = new User();

        BeanUtils.copyProperties(userQuery, tUser);

        //把密码加密
        String encodePwd = passwordEncoder.encode(userQuery.getLoginPwd());
        tUser.setLoginPwd(encodePwd); //密码要加密后存入数据库，不能存明文

        tUser.setEditTime(new Date()); //编辑时间

        //拿到当前登录人的id
        String token = userQuery.getToken();
        //通过token解析出里面的登录id数据
        Integer currentLoginUserId = jwtManager.getLoginUserId(token);

        tUser.setEditBy(currentLoginUserId); //编辑人

        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据用户id批量删除用户
     *
     * @param idList
     * @return
     */
    @Override
    public int batchDelUserById(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<UserInfoDTO> getUserByAll() {
        List<User> userList = tUserMapper.selectByAll();

        return ExtBeanUtils.copyPropertiesForList(userList, UserInfoDTO.class);
    }
}
