package com.rebox.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.StudentScoreInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName StudentScoreInfoMapper
 * @Descrition 学生成绩表 Mapper 接口
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Mapper
public interface StudentScoreInfoMapper extends BaseMapper<StudentScoreInfo> {

}
