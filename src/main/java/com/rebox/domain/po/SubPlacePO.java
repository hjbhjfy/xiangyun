package com.rebox.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("sub_places")  // 对应数据库表
public class SubPlacePO {
    @TableId
    private Long id;

    private String name;
    private String description;
    private Long parentPlaceId;  // 关联主景点ID

    private Timestamp createTime;
    private Long createBy;
    private Timestamp updateTime;
    private Long updateBy;
}