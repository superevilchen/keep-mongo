package com.example.keepmock.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@Slf4j
public class LoggerTasks {

    // * com.journaldev.spring.service.*.get*()

    @After("execution(* services.TaskServiceImpl.*(..))")

//    @After("within(com.example.keepmock.services.TaskServiceImpl)")
    public void LogTaskService(JoinPoint joinPoint){
        System.out.println("jfjdksakllal");
        log.info(joinPoint.toString());
    }
}

//    within(com.howtodoinjava.EmployeeManagerImpl)
