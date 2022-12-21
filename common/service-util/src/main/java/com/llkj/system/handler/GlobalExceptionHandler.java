package com.llkj.system.handler;

import com.llkj.common.result.Result;
import com.llkj.system.exception.LlkjException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author qing
 * @Date 2022/12/17 9:08
 * @Version 1.0
 */
@ControllerAdvice//异常处理类
public class GlobalExceptionHandler {
    /*
    兜底的异常，所有的异常都由这个方法处理的
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error() {
        Result<Object> result =Result.fail();
        return result;
    }
    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseBody
    public Result errorArithmeticException(ArithmeticException exception){
        Result<Object> result = Result.fail().message("除数不能为0"+exception.getMessage());
        return result;
    }
    @ExceptionHandler(value = LlkjException.class)
    @ResponseBody
    public Result errorLlkjException(LlkjException exception){
        exception.printStackTrace();
        Result<Object> result = Result.fail().message("LlkjException自定以异常"+exception.getMessage()).code(20001);
        return result;
    }

}
