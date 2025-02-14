package com.rebox.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.po.CourseInfo;
import com.rebox.domain.po.StudentInfo;
import com.rebox.domain.po.StudentScoreInfo;
import com.rebox.result.RestResult;
import com.rebox.service.CourseInfoService;
import com.rebox.service.StudentInfoService;
import com.rebox.service.StudentScoreInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName StudentScoreInfoController
 * @Descrition 学生成绩表 前端控制器
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
//@Api(value = "学生成绩表", tags = "学生成绩表")
//@RestController
//@RequestMapping("/studentScoreInfo")
public class StudentScoreInfoController {

    @Autowired
    private StudentScoreInfoService studentScoreInfoService;

    @Autowired
    private StudentInfoService studentInfoService;

    @Autowired
    private CourseInfoService courseInfoService;

    @ApiOperation(value = "新增功能", notes = "新增功能")
    @PostMapping("/add")
    public RestResult add(
            @Validated @RequestBody StudentScoreInfo studentScoreInfo
    ) {
        studentScoreInfoService.save(studentScoreInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "更新功能", notes = "更新功能")
    @PostMapping("/update")
    public RestResult update(
            @Validated @RequestBody StudentScoreInfo studentScoreInfo
    ) {
        studentScoreInfoService.updateById(studentScoreInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id进行删除功能", notes = "通过主键id进行删除功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(
            @RequestParam("id") Long id
    ) {
        studentScoreInfoService.removeById(id);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id查询详情功能", notes = "通过主键id查询详情功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/get")
    public RestResult get(
            @RequestParam("id") Long id
    ) {
        StudentScoreInfo byId = studentScoreInfoService.getById(id);
        StudentInfo byId1 = studentInfoService.getById(byId.getStudentId());
        if (byId1 != null) {
            byId.setStudentName(byId1.getName());
        }
        CourseInfo byId2 = courseInfoService.getById(byId.getCourseId());
        if (byId2 != null) {
            byId.setCourseName(byId2.getName());
        }
        return RestResult.SUCCESS(byId);
    }

    @ApiOperation(value = "查询所有功能", notes = "查询所有功能")
    @GetMapping("/list/all")
    public RestResult listAll(
    ) {
        return RestResult.SUCCESS(studentScoreInfoService.list());
    }

    @ApiOperation(value = "后台分页查询功能", notes = "后台分页查询功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", required = false, value = "学生id", paramType = "query"),
            @ApiImplicitParam(name = "courseId", required = false, value = "课程id", paramType = "query"),
            @ApiImplicitParam(name = "grade", required = false, value = "年级", paramType = "query"),
            @ApiImplicitParam(name = "term", required = false, value = "学期", paramType = "query"),
            @ApiImplicitParam(name = "page", required = true, value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "rows", required = true, value = "每页行数", paramType = "query")
    })
    @GetMapping("/list/page")
    public RestResult listStudentScoreInfoPage(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "courseId", required = false) Integer courseId,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "term", required = false) String term,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "rows") Integer rows
    ) {
        LambdaQueryWrapper<StudentScoreInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .eq(ObjectUtils.isNotEmpty(studentId), StudentScoreInfo::getStudentId, studentId)
                .eq(ObjectUtils.isNotEmpty(courseId), StudentScoreInfo::getCourseId, courseId)
                .eq(ObjectUtils.isNotEmpty(grade), StudentScoreInfo::getGrade, grade)
                .eq(ObjectUtils.isNotEmpty(term), StudentScoreInfo::getTerm, term);
        Page<StudentScoreInfo> pageResult = studentScoreInfoService.page(new Page<>(page, rows), lambdaQueryWrapper);
        pageResult.getRecords().forEach(x -> {
            StudentInfo byId1 = studentInfoService.getById(x.getStudentId());
            if (byId1 != null) {
                x.setStudentName(byId1.getName());
            }
            CourseInfo byId2 = courseInfoService.getById(x.getCourseId());
            if (byId2 != null) {
                x.setCourseName(byId2.getName());
            }
        });
        return RestResult.SUCCESS(pageResult);
    }

}
