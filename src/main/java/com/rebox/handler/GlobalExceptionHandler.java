package com.rebox.handler;

import com.rebox.result.RestResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 */
//@ControllerAdvice //切面，spring aop，当@Controller注解的controller发生异常，就会让给类里面的方法去执行
@RestControllerAdvice//切面，spring aop，当@RestController注解的controller发生异常，就会让给类里面的方法去执行
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //只要发生Exception异常 或者 Exception的子类异常，都会回调该方法
    public Object handException(Exception e) {
        //打印异常栈信息
        e.printStackTrace();
        //返回异常结果
        return RestResult.FAIL(500, e.getMessage(), null);
    }

    /**
     * 首页全局异常，先进行精确匹配异常类型，如果没有匹配到，则就找父级异常
     */
}