package ru.test.project.account.balance.service.server.service.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.test.project.account.balance.service.server.annotations.Statistic;
import ru.test.project.account.balance.service.server.annotations.Statistic.ResponseType;
import ru.test.project.account.balance.service.server.service.StatisticInfoService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class StatisticRequestAspect {

    @Qualifier("addRequestStatisticMapServiceImpl")
    private final StatisticInfoService addRequestStatisticInfoService;
    @Qualifier("getRequestStatisticMapServiceImpl")
    private final StatisticInfoService getRequestStatisticInfoService;

    @Around("@annotation(ru.test.project.account.balance.service.server.annotations.Statistic)")
    public Object handlerStatisticAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        saveStatistic(joinPoint);
        return joinPoint.proceed();
    }

    private void saveStatistic(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Statistic statisticAnnotation = method.getAnnotation(Statistic.class);
        ResponseType type = statisticAnnotation.type();

        if (type == ResponseType.ADD) {
            addRequestStatisticInfoService.increment();
        } else if (type == ResponseType.GET) {
            getRequestStatisticInfoService.increment();
        } else {
            throw new RuntimeException("Error request type in statistic annotation.");
        }
    }
}