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
       AOP is adding functionality without breaking business logic
        1. Cross-cutting concerns: Logging, security, transactions, etc.
        2. Separation of concerns: Keeps code clean and maintainable.
        @Aspect =This class does NOT contain business logic.It contains rules about when to run extra code around other methods.
        An Aspect has 3 parts:
            1. Pointcut = (where)A pointcut defines WHERE the extra code should be applied.
                           execution(* com.aop.spring.service..*(..)) method selector
            2. Advice =(when) The extra code to be executed at the defined pointcut.(when should my code run)
                        @Before,@After,@Around,@AfterThrowing
            3. Join Point =(what) A specific point in the program execution, such as method execution.System.out.println("Logging...");
        Package-based AOP is good early, but later it gets noisy.
        That’s why people switch to:@Before("@annotation(LogExecution)")
        
        custom annotation-based AOP:
        1. Define a custom annotation:
           @Retention(RetentionPolicy.RUNTIME)
           @Target(ElementType.METHOD)
           public @interface LogExecution {}
        2. Use the annotation on methods:
           @LogExecution        public String getUserInfo(String username) { ... }
        3. Create an aspect that targets the annotation:    
           @Before("@annotation(com.aop.spring.aop.LogExecution)")
           public void logExecution(JoinPoint joinPoint) {
               System.out.println("Executing method: " + joinPoint.getSignature().getName());
           }                                

        @Before / @After are just subsets of @Around
    */
}
