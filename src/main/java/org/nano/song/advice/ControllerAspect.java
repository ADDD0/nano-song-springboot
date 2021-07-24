package org.nano.song.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * org.nano.song.controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String stringBuilder = "\n" +
                "Request URL:        " + request.getRequestURL() + "\n" +
                "Request Method:     " + request.getMethod() + "\n" +
                "Request IP Address: " + request.getRemoteAddr() + "\n" +
                "Called Class:       " + joinPoint.getSignature().getDeclaringTypeName() + "\n" +
                "Called Method:      " + joinPoint.getSignature().getName() + "\n" +
                "Parameters:         " + Arrays.toString(joinPoint.getArgs());
        logger.info(stringBuilder);
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfter(Object object) {

        ResponseEntity<?> responseEntity = ((ResponseEntity) object);

        String stringBuilder = "\n" +
                "Status Code: " + responseEntity.getStatusCode() + "\n" +
                "Response:    " + responseEntity.getBody();
        logger.info(stringBuilder);
    }
}
