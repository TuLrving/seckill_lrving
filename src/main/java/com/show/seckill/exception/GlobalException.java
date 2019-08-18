package com.show.seckill.exception;

import com.show.seckill.result.CodeMsg;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 09:47
 * @Description: 全局异常
 */
public class GlobalException extends RuntimeException {

    private static final Long SerialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public static Long getSerialVersionUID() {
        return SerialVersionUID;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
