package com.example.demo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("execution(public * com.example..*(..))")
    public void publicMethod() {

    }

    @Before("publicMethod()")
    public void addLog(final JoinPoint joinPoint) {
        System.out.println("*** Executing: " + joinPoint.getSignature());
        Object[] arguments = joinPoint.getArgs();
        for (Object argument : arguments) {
            System.out.println("*** " + argument.getClass().getSimpleName() + " = " + argument);
        }
    }
}
