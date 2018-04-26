package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Aspect
@Component
public class CountAspect {
    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(public * com.example..*(..))")
    public void allMethods() {

    }

    @Around( "allMethods() && @annotation(Count)" )
    public Object profile( final ProceedingJoinPoint joinPoint) throws Throwable {
//        final long start = System.currentTimeMillis();
        try {
            final Object value = joinPoint.proceed();
            return value;
        } catch (Throwable t) {
            throw t;
        } finally {
            Query query = entityManager.createNativeQuery("SELECT count(*) FROM inventory;");
            System.out.println(query.getResultList().get(0) + " vehicles in inventory after "
                    + joinPoint.getSignature().getName() + ".");
//            final long stop = System.currentTimeMillis();
//            System.out.println("Execution time of " + joinPoint.getSignature().getName() +
//                    " was : " + (stop-start));
        }
    }
}
