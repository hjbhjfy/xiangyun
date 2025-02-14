package com.rebox.domain.query;

import lombok.Data;

@Data
public class SubPlaceQuery {
    private Long parentPlaceId;  // 关联主景点ID
    private String name;

    private Integer page;
    private Integer rows;
}