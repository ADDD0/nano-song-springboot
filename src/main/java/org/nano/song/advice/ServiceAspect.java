package org.nano.song.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * org.nano.song.service..*.*(..)) && !execution(public * org.nano.song.service.song.ImportSongService.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        String stringBuilder = "\n" +
                "Called Class:       " + joinPoint.getSignature().getDeclaringTypeName() + "\n" +
                "Called Method:      " + joinPoint.getSignature().getName() + "\n" +
                "Parameters:         " + Arrays.toString(joinPoint.getArgs());
        logger.info(stringBuilder);
    }
}
