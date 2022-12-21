package com.llkj.system.exception;

import com.llkj.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @ClassName LlkjException
 * @Description TODO
 * @Author qing
 * @Date 2022/12/17 9:17
 * @Version 1.0
 */
@Data
public class LlkjException extends RuntimeException {
    private Integer code;
    private String message;

    public LlkjException() {
        super();
    }

    public LlkjException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public LlkjException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LlkjException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
