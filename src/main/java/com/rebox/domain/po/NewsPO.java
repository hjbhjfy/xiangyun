package com.rebox.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("news")
public class NewsPO {
    @TableId
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String createTime;
}