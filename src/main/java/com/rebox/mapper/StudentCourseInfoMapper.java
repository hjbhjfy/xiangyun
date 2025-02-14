package com.rebox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.StudentCourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName StudentCourseInfoMapper
 * @Descrition 学生课程表 Mapper 接口
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Mapper
public interface StudentCourseInfoMapper extends BaseMapper<StudentCourseInfo> {

}
