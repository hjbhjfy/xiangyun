package com.rebox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.PlacePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper extends BaseMapper<PlacePO> {
}
