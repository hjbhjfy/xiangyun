package com.rebox.controller;

import com.rebox.domain.dto.NewsDTO;
import com.rebox.result.RestResult;
import com.rebox.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "新闻管理", tags = "新闻管理 API")
@RestController
@RequestMapping("/news")
@Tag(name = "新闻管理")
public class NewsController {

    @Resource
    private NewsService newsService;

    @ApiOperation(value = "添加新闻", notes = "新增一条新闻记录")
    @PostMapping("/add")
    public RestResult add(@Valid @RequestBody NewsDTO newsDTO) {
        return newsService.addNews(newsDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "更新新闻", notes = "根据ID更新新闻内容")
    @PostMapping("/update")
    public RestResult update(@Valid @RequestBody NewsDTO newsDTO) {
        return newsService.updateNews(newsDTO) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "删除新闻", notes = "根据ID删除新闻")
    @ApiImplicitParam(name = "id", value = "新闻ID", required = true, paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(@RequestParam("id") Long id) {
        return newsService.deleteNews(id) ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    @ApiOperation(value = "查询新闻详情", notes = "根据ID获取新闻内容")
    @ApiImplicitParam(name = "id", value = "新闻ID", required = true, paramType = "query")
    @GetMapping("/get")
    public RestResult getById(@RequestParam("id") Long id) {
        return RestResult.SUCCESS(newsService.getNewsById(id));
    }

    @ApiOperation(value = "查询所有新闻", notes = "获取所有新闻列表")
    @GetMapping("/list/all")
    public RestResult getAll() {
        List<NewsDTO> list = newsService.getAllNews();
        return RestResult.SUCCESS(list);
    }

//    @ApiOperation(value = "分页查询新闻", notes = "根据条件进行分页查询新闻")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "title", value = "新闻标题", paramType = "query"),
//            @ApiImplicitParam(name = "author", value = "作者", paramType = "query"),
//            @ApiImplicitParam(name = "page", value = "当前页码", required = true, paramType = "query"),
//            @ApiImplicitParam(name = "rows", value = "每页条数", required = true, paramType = "query")
//    })
//    @PostMapping("/list")
//    public RestResult list(@RequestBody NewsQuery query) {
//        Page<NewsDTO> page = newsService.getNewsList(query);
//        return RestResult.SUCCESS(page);
//    }
}