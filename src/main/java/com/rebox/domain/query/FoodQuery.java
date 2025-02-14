package com.rebox.domain.query;

import lombok.Data;

@Data
public class FoodQuery {
    private String name;  // 可选，模糊查询
    private Long placeId; // 可选，筛选某个地点的食品

    private Integer page; // 分页
    private Integer rows;
}