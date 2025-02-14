package com.rebox.domain.query;

import lombok.Data;

@Data
public class NewsQuery {
    private String title;
    private Long authorId;
    private Integer page;
    private Integer rows;
}