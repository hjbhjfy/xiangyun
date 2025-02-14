package com.rebox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.po.StudentCourseInfo;
import com.rebox.mapper.StudentCourseInfoMapper;
import com.rebox.service.StudentCourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName StudentCourseInfoServiceImpl
 * @Descrition 学生课程表 服务实现类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentCourseInfoServiceImpl extends ServiceImpl<StudentCourseInfoMapper, StudentCourseInfo> implements StudentCourseInfoService {

    @Autowired
    private StudentCourseInfoMapper studentCourseInfoMapper;

}
