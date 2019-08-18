package com.show.seckill.result;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 07:21
 * @Description: 通用异常处理
 */
public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务器异常");

    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500211, "session不存在或者已失效");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500213, "密码不能为空");
    public static CodeMsg MOBILE_WRONG = new CodeMsg(500214, "手机号码格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500215, "手机号不存在");
    public static CodeMsg PASSWORD_WRONG = new CodeMsg(500216, "密码错误");
    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");
    //秒杀模块 5005XX
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "秒杀已经结束");
    public static CodeMsg REPEAT_MIAO_SHA = new CodeMsg(500501, "重复秒杀");

    public int getCode() {
        return code;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
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

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
