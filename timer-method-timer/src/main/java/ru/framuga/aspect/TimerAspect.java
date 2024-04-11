package ru.framuga.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.framuga.aspect.annotation.RecoverException;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TimerAspect {

    private final TimerProperties properties;

    @Pointcut("within(@ru.framuga.aspect.annotation.Timer *)")
    public void classTimerPointcut() {
    }

    @Pointcut("@annotation(ru.framuga.aspect.annotation.Timer)")
    public void methodTimerPointcut(){

    }

    @Around("classTimerPointcut() || methodTimerPointcut()")
    public Object proceedTimed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        if (properties.isEnableTimer()) {
            long start = System.currentTimeMillis();
            long executionTime = System.currentTimeMillis() - start;
            log.info("Класс = {}", proceedingJoinPoint.getTarget().getClass());
            log.info("Метод = {}", proceedingJoinPoint.getSignature().getName());
            log.info("процесс завершен за " + executionTime + " ms");
            log.info("___________________");
        }
        return proceed;
    }

    @Around("@annotation(ru.framuga.aspect.annotation.RecoverException)")
    public Object returnDefaultValueFromException(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RecoverException annotation = signature.getMethod().getAnnotation(RecoverException.class);
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            for (int i = 0; i < annotation.noRecoverFor().length; i++) {
                if (annotation.noRecoverFor()[i].isAssignableFrom(e.getClass())) {
                    throw e;
                }
            }
            log.warn("Ошибка аспекта " + e.getClass().getName() + " " + e.getMessage());
        }
        return result;
    }
}
