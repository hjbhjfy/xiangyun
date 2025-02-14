package com.rebox.domain.query;

import lombok.Data;

@Data
public class PlaceQuery {
    private String name;
    private String location;

    private Integer page;
    private Integer rows;
}