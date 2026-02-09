package com.aop.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {
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
        Spring AOP does NOT work on internal method calls (self-invocation)
            move the method to another class and call it from there, or use AopContext.currentProxy() to get the proxy object and call the method through it.
            









*/