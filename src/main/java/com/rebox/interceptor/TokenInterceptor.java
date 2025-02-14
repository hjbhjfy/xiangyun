package com.rebox.interceptor;

import com.rebox.constant.Constant;
import com.rebox.enums.CodeEnum;
import com.rebox.manager.JWTManager;
import com.rebox.manager.RedisManager;
import com.rebox.result.RestResult;
import com.rebox.util.JSONUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;

@Component //把拦截器类对象加入到spring容器中
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private JWTManager jwtManager;

    @Resource
    private RedisManager redisManager;

    /**
     * 在进入Controller之前执行，也就先执行该方法，然后再执行controller的方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行preHandle()......, handler = " + handler);

        //请求方法类型是OPTIONS，则放行，不要拦截，因为OPTIONS是属于axios的预检请求
        if (StringUtils.equals(request.getMethod(),  "OPTIONS")) {
            return true;
        }

        //从请求头中获取token
        String token = null;

        if (StringUtils.equals(request.getRequestURI(), "/api/exportExcel")) {
            //表示是导出excel文件的请求，这个请求比较特殊，它的token不在请求头中，而是在url地址参数中
            token = request.getParameter("token");
        } else {
            //其他请求都是在请求头中获取token
            token = request.getHeader("token");
        }

        //判断token是否为空
        if (StringUtils.isBlank(token)) {
            //token为空，不合法的，返回错误响应结果，我们项目统一采用的是RestResult对象返回结果
            RestResult result = RestResult.FAIL(CodeEnum.TOKEN_EMPTY);
            write(response, result);
            return false;
        }

        //判断token有没有被人篡改过（token是jwt生成的，jwt有签名，可以验证签名来判断token有没有被篡改过）
        if (!jwtManager.verifyToken(token)) {
            //token被篡改过了，token验证不通过
            RestResult result = RestResult.FAIL(CodeEnum.TOKEN_SIGN_ILLEGAL);
            write(response, result);
            return false;
        }

        //和redis比较一下，有没有该token
        String redisToken = (String) redisManager.getValue(Constant.REDIS_TOKEN_KEY + token);
        if (!StringUtils.equals(token, redisToken)) {
            //页面传过来的token和redis中存放的token不相同，那么token不合法
            RestResult result = RestResult.FAIL(CodeEnum.TOKEN_ERROR);
            write(response, result);
            return false;
        }

        //以上验证没有问题，则可以执行controller

        //对token续期
        Boolean isRememberMe = Boolean.valueOf(request.getHeader("isRememberMe"));
        if (isRememberMe) {
            redisManager.flushExpireTime(Constant.REDIS_TOKEN_KEY + token, Constant.EXPIRE_TIME, TimeUnit.MINUTES);
        } else {
            redisManager.flushExpireTime(Constant.REDIS_TOKEN_KEY + token, Constant.DEFAUL_EXPIRE_TIME, TimeUnit.MINUTES);
        }

        //返回true的话，那么就可以执行controller的方法，如果返回false，表示不能执行controller的方法
        return true;
    }

    /**
     * 向浏览器输出json结果
     *
     * @param response
     * @param result
     */
    private void write(HttpServletResponse response, RestResult result) {
        response.setContentType("application/json;charset=utf-8");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            //把结果对象转json
            String resultJSON = JSONUtils.writeValueAsString(result);

            //把json结果写出到浏览器前端
            writer.write(resultJSON);

            //刷新一下io流缓存区
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ObjectUtils.isNotEmpty(writer)) {
                writer.close();
            }
        }
    }
}