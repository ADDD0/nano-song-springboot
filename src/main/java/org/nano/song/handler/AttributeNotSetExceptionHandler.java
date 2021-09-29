package org.nano.song.handler;

import lombok.extern.slf4j.Slf4j;
import org.nano.song.handler.exception.AttributeNotSetException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 属性未设置异常处理类
 */
@RestControllerAdvice
@Slf4j
public class AttributeNotSetExceptionHandler {

    @ExceptionHandler(AttributeNotSetException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(AttributeNotSetException exception) {

        String responseBody = exception.getTableName() + "[" + exception.getTableField() + "]" + exception.getMessage();
        log.error(responseBody);

        return ResponseEntity.badRequest().body(responseBody);
    }
}
