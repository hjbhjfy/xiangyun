package com.rebox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.po.CourseInfo;
import com.rebox.mapper.CourseInfoMapper;
import com.rebox.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CourseInfoServiceImpl
 * @Descrition 课程信息表 服务实现类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo> implements CourseInfoService {

    @Autowired
    private CourseInfoMapper courseInfoMapper;

}
