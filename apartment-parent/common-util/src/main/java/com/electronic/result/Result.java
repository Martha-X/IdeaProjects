package com.electronic.result;

import java.io.Serializable;

/**
 * 全局统一返回结果类
 */
public class Result<T> implements Serializable {

    // 状态码
    private Integer code;

    // 消息
    private String message;

    // 数据
    private T data;

    public Result() {
    }

    // 返回数据
    protected static <T> Result<T> build(T data) {
        // 创建结果集对象
        Result<T> result = new Result<T>();
        // 当数据不为空则封装数据给结果集
        if (data != null)
            result.setData(data);
        // 返回结果集对象
        return result;
    }

    /**
     * @return com.electronic.result.Result<T>
     * @Description 构建自定义结果集
     * @Param body, code, message
     * @author electroNic
     * @date 2022/11/28 21:43
     */
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * @return com.electronic.result.Result<T>
     * @Description 构建枚举结果集
     * @Param body, resultCodeEnum
     * @author electroNic
     * @date 2022/11/28 21:43
     */
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * @return com.electronic.result.Result<T>
     * @Description 设置ok
     * @Param none
     * @author electroNic
     * @date 2022/11/28 21:52
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 操作成功
     *
     * @param data baseCategory1List
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return Result.fail(null);
    }

    /**
     * 操作失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if (this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
