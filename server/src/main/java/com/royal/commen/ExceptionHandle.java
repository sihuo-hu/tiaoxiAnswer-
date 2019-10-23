package com.royal.commen;

import com.royal.entity.json.Result;
import com.royal.commen.Logger;;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    static Logger logger = Logger.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        logger.error("【系统异常】{}", e);
        return new Result(e);
    }
}
