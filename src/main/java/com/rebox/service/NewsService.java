package com.rebox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rebox.domain.dto.NewsDTO;
import com.rebox.domain.po.NewsPO;

import java.util.List;


public interface NewsService extends IService<NewsPO> {
    boolean addNews(NewsDTO newsDTO);

    boolean updateNews(NewsDTO newsDTO);

    boolean deleteNews(Long id);

    NewsDTO getNewsById(Long id);

//    Page<NewsDTO> getNewsList(NewsQuery query);

    List<NewsDTO> getAllNews();
}