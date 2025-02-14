package com.rebox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.FoodPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseMapper<FoodPO> {
}