package com.show.seckill.exception;

import com.show.seckill.result.CodeMsg;
import com.show.seckill.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 09:25
 * @Description: 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            logger.error("GlobalExceptionHandler: GlobalException" + e.getMessage());
            return Result.error(globalException.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);//只打印出第一个
            String errorMessage = error.getDefaultMessage();
            logger.error("GlobalExceptionHandler: BindException" + e.getMessage());
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(errorMessage));
        } else {
            logger.error("GlobalExceptionHandler: else" + e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
