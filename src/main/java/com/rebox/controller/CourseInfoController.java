package com.rebox.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.po.CourseInfo;
import com.rebox.result.RestResult;
import com.rebox.service.CourseInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName CourseInfoController
 * @Descrition 课程信息表 前端控制器
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
//@Api(value = "课程信息表", tags = "课程信息表")
//@RestController
//@RequestMapping("/courseInfo")
public class CourseInfoController {

    @Autowired
    private CourseInfoService courseInfoService;

    @ApiOperation(value = "新增功能", notes = "新增功能")
    @PostMapping("/add")
    public RestResult add(
            @Validated @RequestBody CourseInfo courseInfo
    ) {
        courseInfoService.save(courseInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "更新功能", notes = "更新功能")
    @PostMapping("/update")
    public RestResult update(
            @Validated @RequestBody CourseInfo courseInfo
    ) {
        courseInfoService.updateById(courseInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id进行删除功能", notes = "通过主键id进行删除功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(
            @RequestParam("id") Long id
    ) {
        courseInfoService.removeById(id);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id查询详情功能", notes = "通过主键id查询详情功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/get")
    public RestResult get(
            @RequestParam("id") Long id
    ) {
        return RestResult.SUCCESS(courseInfoService.getById(id));
    }

    @ApiOperation(value = "查询所有功能", notes = "查询所有功能")
    @GetMapping("/list/all")
    public RestResult listAll(
    ) {
        return RestResult.SUCCESS(courseInfoService.list());
    }

    @ApiOperation(value = "后台分页查询功能", notes = "后台分页查询功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = false, value = "课程名称", paramType = "query"),
            @ApiImplicitParam(name = "type", required = false, value = "类型", paramType = "query"),
            @ApiImplicitParam(name = "page", required = true, value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "rows", required = true, value = "每页行数", paramType = "query")
    })
    @GetMapping("/list/page")
    public RestResult listCourseInfoPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "rows") Integer rows
    ) {

        LambdaQueryWrapper<CourseInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .like(StringUtils.isNotBlank(name), CourseInfo::getName, name)
                .like(StringUtils.isNotBlank(type), CourseInfo::getType, type);
        return RestResult.SUCCESS(courseInfoService.page(new Page<>(page, rows), lambdaQueryWrapper));
    }

}
