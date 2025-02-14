package com.rebox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.constant.Constant;
import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.dto.UserDTO;
import com.rebox.domain.po.StudentInfo;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.StudentRegisterQuery;
import com.rebox.enums.CodeEnum;
import com.rebox.manager.JWTManager;
import com.rebox.manager.RedisManager;
import com.rebox.mapper.StudentInfoMapper;
import com.rebox.service.StudentInfoService;
import com.rebox.util.JSONUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName StudentInfoServiceImpl
 * @Descrition 学生信息表 服务实现类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JWTManager jwtManager;

    @Resource
    private RedisManager redisManager;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public LoginDTO register(StudentRegisterQuery registerQuery) {
        Long count = studentInfoMapper.selectCount(new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getLoginAct, registerQuery.getLoginAct()));
        if (count > 0) {
            return new LoginDTO(CodeEnum.REGISTER_ACCOUNT_EXIST);
        }
        String encode = passwordEncoder.encode(registerQuery.getLoginPwd());
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(registerQuery, studentInfo);
        studentInfo.setLoginPwd(encode);
        studentInfo.setCreateTime(new Date());
        studentInfoMapper.insert(studentInfo);
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
        StudentInfo studentInfo = studentInfoMapper.selectOne(new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getLoginAct, loginQuery.getLoginAct()));
        System.out.println(studentInfo);
        if (ObjectUtils.isEmpty(studentInfo)) { //用户对象是空，说明用户不存在
            return new LoginDTO(CodeEnum.LOGIN_ACCOUNT_NONE_EXIST);
        }

        //判断密码是否匹配
        if (!passwordEncoder.matches(loginQuery.getLoginPwd(), studentInfo.getLoginPwd())) { //明文和密文不匹配的时候，那么久返回错误提示
            return new LoginDTO(CodeEnum.LOGIN_PASSWORDD_ERROR);
        }

        //以上都验证通过了，那么久登录成功了
        UserDTO userDTO = new UserDTO();

        //Spring框架提供了一个对象属性复制的工具类：BeanUtils
        //把 tUser对象的所有属性值 复制到 userDTO对象属性里面去
        //复制时，需要两个对象的属性名相同、类型也相同才能复制
        BeanUtils.copyProperties(studentInfo, userDTO);

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
            studentInfo.setLastLoginTime(new Date()); //用户最近登录时间
            int update = studentInfoMapper.updateById(studentInfo);
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
}
