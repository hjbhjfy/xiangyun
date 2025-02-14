package com.rebox.domain.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PlaceDTO {
    private Long id;
    private String name;
    private String description;
    private String location;

    private Timestamp createTime;
    private String createByName;
    private Timestamp updateTime;
    private String updateByName;

    private List<SubPlaceDTO> subPlaces;  // 额外字段：包含的次级景点
}