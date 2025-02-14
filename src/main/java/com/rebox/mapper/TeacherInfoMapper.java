package com.rebox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName TeacherInfoMapper
 * @Descrition 教师信息表 Mapper 接口
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {

}
