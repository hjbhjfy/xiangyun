package com.rebox.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO: 数据传输对象
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDTO<T> {

    //当前是第几页
    private int current;

    //每页显示多少条数据
    private int pageSize;

    //总共有多少条数据
    private int totalRow;

    //总共有多少页
    private int totalPage;

    //当前页的数据
    private List<T> dataList;

}