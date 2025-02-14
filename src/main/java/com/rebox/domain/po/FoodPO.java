package com.rebox.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("foods")  // 关联数据库表 foods
public class FoodPO {
    @TableId
    private Long id;

    private String name;
    private String description;
    private Long placeId;  // 关联地点

    private Timestamp createTime;
    private Long createBy;
    private Timestamp updateTime;
    private Long updateBy;
}