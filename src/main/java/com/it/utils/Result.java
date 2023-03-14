package com.it.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义结果集
 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {


    private boolean status;

    /**
     * 返回状态码 200是正常 非200表示数据异常
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;


    public static Result error(boolean status,String code,String message) {
        Result result = new Result();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result success(boolean status,String code,String message) {
        Result result = new Result();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
