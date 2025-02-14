package com.rebox.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.po.StudentInfo;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.StudentRegisterQuery;

/**
 * @ClassName StudentInfoService
 * @Descrition 学生信息表 服务类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
public interface StudentInfoService extends IService<StudentInfo> {

    LoginDTO register(StudentRegisterQuery registerQuery);

    LoginDTO login(LoginQuery loginQuery);

    Boolean logout(String token);
}
