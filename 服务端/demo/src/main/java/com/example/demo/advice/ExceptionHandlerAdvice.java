package com.example.demo.advice;

import com.example.demo.domin.Response;
import com.example.demo.exception.CommonException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.util.Constant;
import com.example.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.example.demo.util.Constant.ERROR_NOT_FOUND;
import static com.example.demo.util.Constant.ERROR_NOT_FOUND_MESSAGE;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 处理自定义异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    public @ResponseBody
    Response commonException(CommonException exception) {
        return ResponseUtil.failed(exception.getStatus(), exception.getMessage(), exception.getDetail());
    }

    /**
     * 访问了不存在的资源
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response notFoundException(Exception exception) {
        return ResponseUtil.failed(ERROR_NOT_FOUND, ERROR_NOT_FOUND_MESSAGE);
    }

    /**
     * 违反了唯一约束异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Response duplicateKeyException(DuplicateKeyException exception) {
        return ResponseUtil.failed(Constant.ERROR_DATA_EXIST, Constant.ERROR_DATA_EXIST_MESSAGE);
    }

    /**
     * 默认异常处理器
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    Response defaultHandleException(Exception exception) {
        log.error("defaultHandleException {}", exception);
        return ResponseUtil.failed();
    }
}
