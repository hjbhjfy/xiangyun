package com.rebox.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.po.StudentInfo;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.StudentRegisterQuery;
import com.rebox.enums.CodeEnum;
import com.rebox.result.RestResult;
import com.rebox.service.StudentInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName StudentInfoController
 * @Descrition 学生信息表 前端控制器
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
//@Api(value = "学生信息表", tags = "学生信息表")
//@RestController
//@RequestMapping("/studentInfo")
public class StudentInfoController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentInfoService studentInfoService;

    @Operation(summary = "注册的http接口（restFul接口）")
    @PostMapping(value = "/api/register")
    public RestResult register(@RequestBody StudentRegisterQuery registerQuery) {
        if (StringUtils.isBlank(registerQuery.getLoginAct())) {
            return RestResult.FAIL(CodeEnum.LOGIN_ACCOUNT_EMPTY);
        }
        if (StringUtils.isBlank(registerQuery.getLoginPwd())) {
            return RestResult.FAIL(CodeEnum.LOGIN_PASSWORD_EMPTY);
        }
        //登录业务处理
        LoginDTO loginDTO = studentInfoService.register(registerQuery);
        if (loginDTO.getCodeEnum() == CodeEnum.SUCCESS) {
            //登录成功
            return RestResult.SUCCESS(loginDTO);

        }

        //登录失败
        return RestResult.FAIL(loginDTO.getCodeEnum());
    }

    /**
     * 登录的http接口（restFul接口）
     *
     * @param loginQuery
     * @return
     */
    @Operation(summary = "登录的http接口（restFul接口）")
    @Parameters({
            @Parameter(name = "loginAct", description = "登录账号", in = ParameterIn.QUERY),
            @Parameter(name = "loginPwd", description = "登录密码", in = ParameterIn.QUERY),
            @Parameter(name = "isRememberMe", description = "是否记住我", in = ParameterIn.QUERY),
    })
    @PostMapping(value = "/api/login")
    public RestResult login(@RequestBody LoginQuery loginQuery) {
        //axios库通过post请求提交过来的参数的是json结构
        //@RequestBody注解接收json格式的参数

        //controller中一般要做一些参数验证工作
        if (StringUtils.isBlank(loginQuery.getLoginAct())) {
            //StringUtils.isBlank() // “” 、null、 空格
            //StringUtils.isEmpty() // “” 和  null
            return RestResult.FAIL(CodeEnum.LOGIN_ACCOUNT_EMPTY);
        }

        if (StringUtils.isBlank(loginQuery.getLoginPwd())) {
            return RestResult.FAIL(CodeEnum.LOGIN_PASSWORD_EMPTY);
        }
        //登录业务处理
        LoginDTO loginDTO = studentInfoService.login(loginQuery);
        if (loginDTO.getCodeEnum() == CodeEnum.SUCCESS) {
            //登录成功
            return RestResult.SUCCESS(loginDTO);

        }

        //登录失败
        return RestResult.FAIL(loginDTO.getCodeEnum());
        //return RestResult.FAIL();
    }

    /**
     * 免登录
     *
     * @return
     */
    @Operation(summary = "免登录")
    @GetMapping(value = "/api/freeLogin")
    public RestResult freeLogin() {
        //免登录的controller方法中不需要写任何业务代码，因为前端请求后端时，首先会在springboot拦截器中拦截
        //springboot拦截器已经验证了token的合法性
        return RestResult.SUCCESS();
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Operation(summary = "退出登录")
    @Parameter(name = "token", description = "token", in = ParameterIn.HEADER)
    @GetMapping(value = "/api/logout")
    public RestResult logout(@RequestHeader(value = "token") String token) {
        Boolean remove = studentInfoService.logout(token);
        return remove ? RestResult.SUCCESS() : RestResult.FAIL();
    }


    @ApiOperation(value = "新增功能", notes = "新增功能")
    @PostMapping("/add")
    public RestResult add(
            @Validated @RequestBody StudentInfo studentInfo
    ) {
        long count = studentInfoService.count(new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getLoginAct, studentInfo.getLoginAct()));
        if (count > 0) {
            return RestResult.FAIL(500, "账号已存在", null);
        }
        //把密码加密
        String encodePwd = passwordEncoder.encode(studentInfo.getLoginPwd());
        studentInfo.setLoginPwd(encodePwd); //密码要加密后存入数据库，不能存明文
        studentInfoService.save(studentInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "更新功能", notes = "更新功能")
    @PostMapping("/update")
    public RestResult update(
            @Validated @RequestBody StudentInfo studentInfo
    ) {
        //把密码加密
        String encodePwd = passwordEncoder.encode(studentInfo.getLoginPwd());
        studentInfo.setLoginPwd(encodePwd); //密码要加密后存入数据库，不能存明文
        studentInfoService.updateById(studentInfo);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id进行删除功能", notes = "通过主键id进行删除功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/delete")
    public RestResult delete(
            @RequestParam("id") Long id
    ) {
        studentInfoService.removeById(id);
        return RestResult.SUCCESS();
    }

    @ApiOperation(value = "通过主键id查询详情功能", notes = "通过主键id查询详情功能")
    @ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query")
    @GetMapping("/get")
    public RestResult get(
            @RequestParam("id") Long id
    ) {
        return RestResult.SUCCESS(studentInfoService.getById(id));
    }

    @ApiOperation(value = "查询所有功能", notes = "查询所有功能")
    @GetMapping("/list/all")
    public RestResult listAll(
    ) {
        return RestResult.SUCCESS(studentInfoService.list());
    }

    @ApiOperation(value = "后台分页查询功能", notes = "后台分页查询功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAct", required = false, value = "账号", paramType = "query"),
            @ApiImplicitParam(name = "name", required = false, value = "名称", paramType = "query"),
            @ApiImplicitParam(name = "phone", required = false, value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "email", required = false, value = "邮箱", paramType = "query"),
            @ApiImplicitParam(name = "grade", required = false, value = "年级", paramType = "query"),
            @ApiImplicitParam(name = "page", required = true, value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "rows", required = true, value = "每页行数", paramType = "query")
    })
    @GetMapping("/list/page")
    public RestResult listStudentInfoPage(
            @RequestParam(value = "loginAct", required = false) String loginAct,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "rows") Integer rows
    ) {

        LambdaQueryWrapper<StudentInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .like(StringUtils.isNotBlank(loginAct), StudentInfo::getLoginAct, loginAct)
                .like(StringUtils.isNotBlank(name), StudentInfo::getName, name)
                .like(StringUtils.isNotBlank(phone), StudentInfo::getPhone, phone)
                .like(StringUtils.isNotBlank(email), StudentInfo::getEmail, email)
                .like(StringUtils.isNotBlank(grade), StudentInfo::getGrade, grade);
        return RestResult.SUCCESS(studentInfoService.page(new Page<>(page, rows), lambdaQueryWrapper));
    }


}
