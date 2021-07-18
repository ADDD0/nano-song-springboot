package org.nano.song.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.nano.song.domain.Constant;
import org.nano.song.controller.handler.exception.BindRelationExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BindRelationExistExceptionHandler {

    @ExceptionHandler(BindRelationExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(BindRelationExistException exception) {

        String responseBody = exception.getTableName1() + "[" + exception.getTableField1() + "]与"
                + exception.getTableName2() + "[" + exception.getTableField2() + "]" + Constant.MSG_BIND;
        log.error(responseBody);

        return ResponseEntity.badRequest().body(responseBody);
    }
}
