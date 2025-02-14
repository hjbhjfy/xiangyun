package com.rebox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rebox.domain.po.NewsPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper extends BaseMapper<NewsPO> {
}
