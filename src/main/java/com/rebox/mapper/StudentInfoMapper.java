package com.rebox.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName StudentInfoMapper
 * @Descrition 学生信息表 Mapper 接口
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

}
