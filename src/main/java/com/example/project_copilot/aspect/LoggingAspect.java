
package com.example.project_copilot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.project_copilot.service.*.*(..))")
    private void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Entering {}.{} with arguments: {}", 
            className, methodName, joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.example.project_copilot.service.*.get*(..))", 
                    returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} returned: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in {}: {} - {}", 
            methodName, exception.getClass().getName(), exception.getMessage());
    }

    @Around("execution(* com.example.project_copilot.service.*.delete*(..))")
    public Object logAroundDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("{} completed in {} ms", methodName, duration);
            return result;
        } catch (Exception e) {
            logger.error("{} failed after {} ms", 
                methodName, System.currentTimeMillis() - startTime);
            throw e;
        }
    }

    @After("execution(* com.example.project_copilot.service.HouseholdService.getHouseholdStatistics())")
    public void logAfterStatistics(JoinPoint joinPoint) {
        logger.info("Statistics gathering completed");
    }
}