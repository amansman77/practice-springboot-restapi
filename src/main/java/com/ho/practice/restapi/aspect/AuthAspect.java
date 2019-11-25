package com.ho.practice.restapi.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.service.AuthService;
import com.ho.practice.restapi.util.JwtTokenUtil;

@Aspect
@Component
public class AuthAspect {

	@Autowired
	AuthService authService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {
	}

	@Pointcut("execution(* com.ho.practice.restapi.controller..unAuth*(..))")
	public void unAuthAPI() {
	}
	
	@Before("restController() && !unAuthAPI()")
	public void validateAuth(JoinPoint joinPoint) throws UnauthorizedException, NotSupportedException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    authService.isValidToken(request.getHeader("Authorization"));
	}
	
}
