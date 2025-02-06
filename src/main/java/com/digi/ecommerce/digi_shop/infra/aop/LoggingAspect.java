package com.digi.ecommerce.digi_shop.infra.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Before("execution(* com.digi.ecommerce.digi_shop.service.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Method called: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "execution(* com.digi.ecommerce.digi_shop.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method executed: {} returned: {}", methodName, result);
    }
}
