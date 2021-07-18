package org.nano.song.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.nano.song.domain.Constant;
import org.nano.song.controller.handler.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(ResourceNotFoundException exception) {

        String responseBody = exception.getTableName() + "[" + exception.getTableField() + "]" + Constant.MSG_NOT_EXIST;
        log.error(responseBody);

        return ResponseEntity.badRequest().body(responseBody);
    }
}
