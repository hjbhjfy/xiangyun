package com.rebox.domain.dto;

import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}