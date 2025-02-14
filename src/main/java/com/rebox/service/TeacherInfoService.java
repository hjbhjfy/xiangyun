package com.rebox.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.po.TeacherInfo;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.TeacerRegisterQuery;

/**
 * @ClassName TeacherInfoService
 * @Descrition 教师信息表 服务类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
public interface TeacherInfoService extends IService<TeacherInfo> {

    LoginDTO register(TeacerRegisterQuery registerQuery);

    LoginDTO login(LoginQuery loginQuery);

    Boolean logout(String token);

}
