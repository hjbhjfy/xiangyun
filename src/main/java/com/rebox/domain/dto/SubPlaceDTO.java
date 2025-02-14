package com.rebox.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SubPlaceDTO {
    private Long id;
    private String name;
    private String description;
    private Long parentPlaceId;  // 关联主景点ID

    private String parentPlaceName;  // 额外字段：主景点名称
    private Timestamp createTime;
    private String createByName;
    private Timestamp updateTime;
    private String updateByName;
}