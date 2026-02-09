package com.aop.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    

    @Around("@annotation(com.aop.spring.annotation.LogExecution)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println("Before method: " + joinPoint.getSignature().getName());
        // THIS LINE ACTUALLY CALLS THE REAL METHOD
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println(
            "After method: " + joinPoint.getSignature().getName() +
            " | Execution time: " + (endTime - startTime) + " ms"
        );

        return result; // VERY IMPORTANT
    }





    // Before any method in UserService runs
    //@Before("execution(* com.aop.spring.service.UserService.*(..))")//.. → any sub-package  * → any class *(..) → any method, any args
    @Before("@annotation(com.aop.spring.annotation.LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }

    // After any method in UserService runs
    @After("execution(* com.aop.spring.service.UserService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature().getName());
    }

    /*
       
    */
}
