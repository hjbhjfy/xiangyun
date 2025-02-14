package com.rebox.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("places")  // 对应数据库表
public class PlacePO {
    @TableId
    private Long id;

    private String name;
    private String description;
    private String location;  // 景点地址

    private Timestamp createTime;
    private Long createBy;
    private Timestamp updateTime;
    private Long updateBy;
}