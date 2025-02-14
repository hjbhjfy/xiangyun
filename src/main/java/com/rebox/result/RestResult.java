package com.rebox.result;

import com.rebox.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局统一返回结果类
 *
 * {
 *     code : 200,
 *     msg : "操作成功",
 *     data : {
 *         id : 1028,
 *         name : "cat",
 *         phone : "13700000000”
 *     }
 * }
 *
 */
@AllArgsConstructor //lombok、生成带所有参数的构造方法
@NoArgsConstructor //lombok、生成无参的构造方法
@Data //lombok、生成set、get方法
public class RestResult {

    //结果码（错误码）
    private int code;

    //结果信息（错误信息）
    private String msg;

    //结果数据（具体数据）
    private Object data;

    public static RestResult SUCCESS() {
        return new RestResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), null);
    }

    public static RestResult SUCCESS(Object data) {
        return new RestResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), data);
    }

    public static RestResult SUCCESS(int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }

    //---------------------------------------------------

    public static RestResult FAIL() {
        return new RestResult(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg(), null);
    }

    public static RestResult FAIL(Object data) {
        return new RestResult(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg(), data);
    }

    public static RestResult FAIL(CodeEnum codeEnum) {
        return new RestResult(codeEnum.getCode(), codeEnum.getMsg(), null);
    }

    public static RestResult FAIL(int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }
}
