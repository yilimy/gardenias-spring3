package com.example.boot3.valid.exception;

import com.example.boot3.valid.config.FastFailedValidConfig;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * @author caimeng
 * @date 2024/5/17 18:13
 */
@ControllerAdvice
@Slf4j
public class ValidationHandler {
    /**
     * 配置自定义校验器，可改变结果中的数量 {@link FastFailedValidConfig#fastFailedValidator(AutowireCapableBeanFactory)}
     * @param exception 校验异常
     * @return 异常信息
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object handleConstraintViolationException(ConstraintViolationException exception) {
        log.info("ConstraintViolationException 异常", exception);
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        log.error("校验失败结果数量: {}", constraintViolations.size());
        return constraintViolations.stream().map(ConstraintViolation::getMessage).findAny().orElse(null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("MethodArgumentNotValidException异常", exception);
        List<ObjectError> objectErrorList = exception.getBindingResult().getAllErrors();
        log.error("校验失败结果数量: {}", objectErrorList.size());
        ObjectError objectError = objectErrorList.get(0);
        return objectError.getDefaultMessage();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleUnCatchedException(Exception exception) {
        log.info("Exception异常", exception);
        return exception.getMessage();
    }
}
