package com.rebox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.po.StudentScoreInfo;
import com.rebox.mapper.StudentScoreInfoMapper;
import com.rebox.service.StudentScoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName StudentScoreInfoServiceImpl
 * @Descrition 学生成绩表 服务实现类
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentScoreInfoServiceImpl extends ServiceImpl<StudentScoreInfoMapper, StudentScoreInfo> implements StudentScoreInfoService {

    @Autowired
    private StudentScoreInfoMapper studentScoreInfoMapper;

}
