package org.nano.song.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * DAO层切面类
 */
@Aspect
@Component
public class RepositoryAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * org.nano.song.domain.repository..*.save(..))")
    public void saveLog() {
    }

    @Before("saveLog()")
    public void doBefore(JoinPoint joinPoint) {

        String stringBuilder = "\n" +
                "Called Class:       " + joinPoint.getSignature().getDeclaringTypeName() + "\n" +
                "Called Method:      " + joinPoint.getSignature().getName() + "\n" +
                "Parameters:         " + Arrays.toString(joinPoint.getArgs());
        logger.info(stringBuilder);
    }

    @AfterReturning(returning = "object", pointcut = "saveLog()")
    public void doAfter(Object object) {

        String stringBuilder = "\n" +
                "Save Result:        " + object;
        logger.info(stringBuilder);
    }
}
