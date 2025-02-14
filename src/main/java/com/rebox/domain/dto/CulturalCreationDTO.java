package com.rebox.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CulturalCreationDTO {
    private Long id;
    private String name;
    private String description;
    private Long placeId;  // 关联地点

    private String placeName;  // 额外字段：地点名称
    private Timestamp createTime;
    private String createByName;  // 额外字段：创建者名称
    private Timestamp updateTime;
    private String updateByName;  // 额外字段：更新者名称
}