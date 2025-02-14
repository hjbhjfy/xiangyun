package com.rebox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rebox.domain.dto.NewsDTO;
import com.rebox.domain.po.NewsPO;
import com.rebox.mapper.NewsMapper;
import com.rebox.mapper.UserMapper;
import com.rebox.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, NewsPO> implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean addNews(NewsDTO newsDTO) {
        NewsPO newsPO = new NewsPO();
        BeanUtils.copyProperties(newsDTO, newsPO);
        return save(newsPO);
    }

    @Override
    public boolean updateNews(NewsDTO newsDTO) {
        NewsPO newsPO = new NewsPO();
        BeanUtils.copyProperties(newsDTO, newsPO);
        return updateById(newsPO);
    }

    @Override
    public boolean deleteNews(Long id) {
        return removeById(id);
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        NewsPO newsPO = getById(id);
        NewsDTO newsDTO = new NewsDTO();
        BeanUtils.copyProperties(newsPO, newsDTO);
        return newsDTO;
    }

//    @Override
//    public Page<NewsDTO> getNewsList(NewsQuery query) {
//        LambdaQueryWrapper<NewsPO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.like(query.getTitle() != null, NewsPO::getTitle, query.getTitle());
//        wrapper.eq(query.getAuthor() != null, NewsPO::getAuthor, query.getAuthor());
//
//        Page<NewsPO> page = newsMapper.selectPage(new Page<>(query.getPage(), query.getRows()), wrapper);
//        return page.convert(po -> {
//            NewsDTO dto = new NewsDTO();
//            BeanUtils.copyProperties(po, dto);
//            return dto;
//        });
//    }


//    @Override
//    public Page<NewsDTO> getNewsList(NewsQuery query) {
//        LambdaQueryWrapper<NewsPO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.like(query.getTitle() != null, NewsPO::getTitle, query.getTitle());
//        wrapper.eq(query.getAuthorId() != null, NewsPO::getAuthorId, query.getAuthorId());
//
//        // 1️⃣ 查询 Page<NewsPO>
//        Page<NewsPO> poPage = newsMapper.selectPage(new Page<>(query.getPage(), query.getRows()), wrapper);
//
//        // 2️⃣ 手动转换 Page<NewsPO> 到 Page<NewsDTO>
//        Page<NewsDTO> dtoPage = new Page<>();
//        dtoPage.setCurrent(poPage.getCurrent());
//        dtoPage.setSize(poPage.getSize());
//        dtoPage.setTotal(poPage.getTotal());
//
//        // 3️⃣ 遍历转换 NewsPO → NewsDTO
//        dtoPage.setRecords(
//                poPage.getRecords().stream().map(po -> {
//                    NewsDTO dto = new NewsDTO();
//                    BeanUtils.copyProperties(po, dto);
//
//                    // 4️⃣ 查询用户表，获取作者名称
//                    if (po.getAuthorId() != null) {
//                        User author = userMapper.selectById(po.getAuthorId()); // 确保 UserMapper 使用 Long
//                        if (author != null) {
//                            dto.setAuthor(author.getName());  // 赋值作者名称
//                        }
//                    }
//
//                    return dto;
//                }).collect(Collectors.toList())
//        );
//
//        return dtoPage;
//    }


    @Override
    public List<NewsDTO> getAllNews() {
        return list().stream().map(po -> {
            NewsDTO dto = new NewsDTO();
            BeanUtils.copyProperties(po, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}