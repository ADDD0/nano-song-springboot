package org.nano.song.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ValidatedExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(e -> {
                FieldError fieldError = (FieldError) e;
                log.error("参数错误: entity[{}], field[{}], message[{}]", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                stringBuilder.append(fieldError.getDefaultMessage()).append("\n");
            });
        }
        return ResponseEntity.badRequest().body(stringBuilder);
    }
}
