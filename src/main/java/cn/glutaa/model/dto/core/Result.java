package cn.glutaa.model.dto.core;

import lombok.Data;

@Data
public class Result {

    private int code;
    private String message;
    Object data;

    ///** 构造方法1 **///
    public Result() {
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    ///** 构造方法2 **///
    public Result(StatusCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(StatusCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    ///** 设置指定值 **///
    public Result setCode(int code){
        this.code = code;
        return this;
    }

    public Result setMsg(String message){
        this.message = message;
        return this;
    }

    public Result setData(Object data){
        this.data = data;
        return this;
    }


    ///** SET **///
    public static Result SET(int code, String message){
        return new Result(code, message);
    }

    public static Result SET(int code, String message, Object data){
        return new Result(code, message, data);
    }

    ///** SUCCESS **///
    public static Result SUCCESS() {
        return new Result(StatusCode.SUCCESS);
    }

    public static Result SUCCESS(Object data) {
        return new Result(StatusCode.SUCCESS, data);
    }

    ///** FAIL **///
    public static Result FAIL() {
        return new Result(StatusCode.FAIL);
    }

    ///** PARAM_INVALID **///
    public static Result PARAM_INVALID() {
        return new Result(StatusCode.PARAM_INVALID);
    }

    ///** NOT_AUTHORIZED **///
    public static Result NOT_AUTHORIZED() {
        return new Result(StatusCode.NOT_AUTHORIZED);
    }

    ///** SYSTEM_ERROR **///
    public static Result SYSTEM_ERROR() {
        return new Result(StatusCode.SYSTEM_ERROR);
    }

    ///** 用户相关 **///
    public static Result WECHAT_USER_IDENTIFY_FAILED() {
        return new Result(StatusCode.WECHAT_USER_IDENTIFY_FAILED);
    }
    public static Result ACCOUNT_UNREGISTERED() {
        return new Result(StatusCode.Account_UNREGISTERED);
    }
}

enum StatusCode {

    ///** 常用 **///
    SUCCESS(20000, "请求成功！"),
    FAIL(40000, "请求失败！"),
    PARAM_INVALID(40100, "参数无效!"),
    NOT_AUTHORIZED(40200, "没有权限！"),
    SYSTEM_ERROR(50000, "系统异常！"),

    ///** 用户相关 **///
    WECHAT_USER_IDENTIFY_FAILED(40001, "微信身份认证失败！"),
    Account_UNREGISTERED(40002, "账户未注册！");





    //状态码
    int code;
    //提示信息
    String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}