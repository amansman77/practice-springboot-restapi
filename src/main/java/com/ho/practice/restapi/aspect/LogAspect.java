package com.ho.practice.restapi.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {
	}

	@Around("restController()")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		StopWatch sw = new StopWatch();
		
		sw.start();
		Object obj = joinPoint.proceed();
		sw.stop();
		log.info("Url path : " + request.getRequestURL().toString()
			+ ", Execution time : " + sw.getTotalTimeMillis() + " ms");
		
		return obj;
	}
	
	@AfterThrowing(pointcut="restController()", throwing="ex")
	public void afterThrow(JoinPoint joinPoint, Throwable ex) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		log.error("Url path : " + request.getRequestURL().toString() + ", " + ex.getClass().getName() + " : " + ex.getMessage());
	}
	
}
