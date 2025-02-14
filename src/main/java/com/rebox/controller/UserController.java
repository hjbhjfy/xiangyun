package com.rebox.controller;

import com.rebox.domain.dto.LoginDTO;
import com.rebox.domain.dto.PageDTO;
import com.rebox.domain.dto.UserInfoDTO;
import com.rebox.domain.query.LoginQuery;
import com.rebox.domain.query.RegisterQuery;
import com.rebox.domain.query.UserQuery;
import com.rebox.enums.CodeEnum;
import com.rebox.result.RestResult;
import com.rebox.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

//用户
@CrossOrigin //解决跨域问题
@RestController //返回json
@Tag(name = "人员中心")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "注册的http接口（restFul接口）")
    @PostMapping(value = "/api/register")
    public RestResult register(@RequestBody RegisterQuery registerQuery) {
        if (StringUtils.isBlank(registerQuery.getLoginAct())) {
            return RestResult.FAIL(CodeEnum.LOGIN_ACCOUNT_EMPTY);
        }
        if (StringUtils.isBlank(registerQuery.getLoginPwd())) {
            return RestResult.FAIL(CodeEnum.LOGIN_PASSWORD_EMPTY);
        }
        //登录业务处理
        LoginDTO loginDTO = userService.register(registerQuery);
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
            @Parameter(name = "loginAct",description = "登录账号",in = ParameterIn.QUERY),
            @Parameter(name = "loginPwd",description = "登录密码",in = ParameterIn.QUERY),
            @Parameter(name = "isRememberMe",description = "是否记住我",in = ParameterIn.QUERY),
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
        LoginDTO loginDTO = userService.login(loginQuery);
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
    @Parameter(name = "token",description = "token",in = ParameterIn.HEADER)
    @GetMapping(value = "/api/logout")
    public RestResult logout(@RequestHeader(value = "token") String token) {

        // /api/user?id=1209  @RequestParam()
        // /api/user/1209     @PathVariable()
        // header [token==1231231]   @RequestHeader()
        Boolean remove = userService.logout(token);
        return remove ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    /**
     * 分页查询用户列表数据
     *
     * @param current
     * @return
     */
    @Operation(summary = "分页查询用户列表数据")
    @Parameter(name = "current",description = "要查询的页码")
    @GetMapping(value = "/api/users")
    public RestResult users(@RequestParam(value = "current", required = false) Integer current) {
        // required = false 表示current可以传，也可以不传
        if (current == null) {
            current = 1;
        }
        //分页查询的结果用PageDTO进行封装
        PageDTO<UserInfoDTO> pageDTO = userService.getUserByPage(current);

        //有的时候，查询可以不判断成功与失败，不管查没查到数据，都返回200，页面上去控制显示
        return RestResult.SUCCESS(pageDTO);
    }

    /**
     * 新增用户（提交保存）
     *
     * @param userQuery
     * @return
     */
    @Operation(summary = "新增用户（提交保存）")
    @Parameters({
            @Parameter(name = "id",description = "用户id",in = ParameterIn.QUERY),
            @Parameter(name = "loginAct",description = "登录账号",in = ParameterIn.QUERY),
            @Parameter(name = "loginPwd",description = "登录密码",in = ParameterIn.QUERY),
            @Parameter(name = "name",description = "用户姓名",in = ParameterIn.QUERY),
            @Parameter(name = "phone",description = "用户手机",in = ParameterIn.QUERY),
            @Parameter(name = "email",description = "用户邮箱",in = ParameterIn.QUERY),
            @Parameter(name = "role",description = "用户角色(1:超级管理员，2:藏品管理员，3:安保管理员)",in = ParameterIn.QUERY),
            @Parameter(name = "expireTime",description = "账号过期时间",in = ParameterIn.QUERY),
            @Parameter(name = "state",description = "账号状态(0正常，1锁定，2禁用)",in = ParameterIn.QUERY),
            @Parameter(name = "token",description = "token",in = ParameterIn.HEADER)
    })
    @PostMapping(value = "/api/user")
    public RestResult addUser(UserQuery userQuery, @RequestHeader(value = "token") String token) {
        if (userQuery.getRole()>3&&userQuery.getRole()<1){
            return RestResult.FAIL("用户角色只能输入1-3的数字！");
        }
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);

        return save >= 1 ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据用户id查询用户信息")
    @Parameter(name = "id",description = "用户id(主键)",in = ParameterIn.PATH)
    @GetMapping(value = "/api/user/{id}")
    public RestResult queryUser(@PathVariable(value = "id") Integer id) {
        UserInfoDTO userInfoDTO = userService.getUserById(id);

        return RestResult.SUCCESS(userInfoDTO);
    }

    /**
     * 编辑用户（提交保存）
     *
     * @param userQuery
     * @return
     */
    @Operation(summary = "编辑用户（提交保存）")
    @Parameters({
            @Parameter(name = "id",description = "用户id",in = ParameterIn.QUERY),
            @Parameter(name = "loginAct",description = "登录账号",in = ParameterIn.QUERY),
            @Parameter(name = "loginPwd",description = "登录密码",in = ParameterIn.QUERY),
            @Parameter(name = "name",description = "用户姓名",in = ParameterIn.QUERY),
            @Parameter(name = "phone",description = "用户手机",in = ParameterIn.QUERY),
            @Parameter(name = "email",description = "用户邮箱",in = ParameterIn.QUERY),
            @Parameter(name = "role",description = "用户角色",in = ParameterIn.QUERY),
            @Parameter(name = "expireTime",description = "账号过期时间",in = ParameterIn.QUERY),
            @Parameter(name = "state",description = "账号状态(0正常，1锁定，2禁用)",in = ParameterIn.QUERY),
            @Parameter(name = "token",description = "token",in = ParameterIn.HEADER)
    })
    @PutMapping(value = "/api/user")
    public RestResult editUser(UserQuery userQuery, @RequestHeader(value = "token") String token) {
        if (userQuery.getRole()>3&&userQuery.getRole()<1){
            return RestResult.FAIL("用户角色只能输入1-3的数字！");
        }
        userQuery.setToken(token);

        int update = userService.updateUser(userQuery);
        return update >= 1 ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    /**
     * 根据用户id删除用户
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据用户id删除用户")
    @Parameter(name = "id",description = "用户id(主键)",in = ParameterIn.PATH)
    @DeleteMapping(value = "/api/user/{id}")
    public RestResult delUser(@PathVariable(value = "id") Integer id) {
        int del = userService.delUserById(id);

        return del >= 1 ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    /**
     * 根据用户id批量删除用户
     *
     * @param ids
     * @return
     */
    @Operation(summary = "根据用户id批量删除用户")
    @Parameter(name = "ids",description = "用户ids(1,2,5,7,10,12)",in = ParameterIn.PATH)
    @DeleteMapping(value = "/api/user/batch/{ids}")
    public RestResult batchDelUser(@PathVariable(value = "ids") String ids) {
        //ids = "1,2,5,7,10,12" , ids.split(",")是把ids转成数组, Arrays.asList()是把数组转成List
        List<String> idList = Arrays.asList(ids.split(","));

        int del = userService.batchDelUserById(idList);

        return del >= idList.size() ? RestResult.SUCCESS() : RestResult.FAIL();
    }

    /**
     * 查询所有用户数据
     *
     * @return
     */
    @Operation(summary = "查询所有用户数据")
    @GetMapping(value = "/api/user/all")
    public RestResult allUser() {
        List<UserInfoDTO> userInfoDTOList = userService.getUserByAll();

        return RestResult.SUCCESS(userInfoDTOList);
    }
}