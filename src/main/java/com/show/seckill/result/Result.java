package com.show.seckill.result;

import java.io.Serializable;

/**
 * @Auther: 涂成
 * @Date: 2019/6/18 20:53
 * @Description: 返回给前端的结果
 */
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;


    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<>(cm);
    }


    private Result() {
    }

    /**
     * 成功时调用
     *
     * @param data
     */
    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    /**
     * 失败时调用
     *
     * @param codeMsg
     */
    public Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
