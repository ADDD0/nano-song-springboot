package org.nano.song.handler;

import lombok.extern.slf4j.Slf4j;
import org.nano.song.domain.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

/**
 * 格式转换异常处理类
 */
@RestControllerAdvice
@Slf4j
public class ParseExceptionHandler {

    @ExceptionHandler(ParseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(ParseException exception) {

        String responseBody = "[" + exception.getMessage() + "]" + Constant.ERR_MSG_DATE_FOMAT_WRONG;
        log.error(responseBody);

        return ResponseEntity.badRequest().body(responseBody);
    }
}
