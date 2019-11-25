package com.ho.practice.restapi.advice;

import javax.transaction.NotSupportedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ho.practice.restapi.dto.response.ResponseDto;
import com.ho.practice.restapi.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
	
	/*
	 * 400 코드 처리
	 */
	@ExceptionHandler(value = {NotSupportedException.class})
	public ResponseEntity<ResponseDto> unauthorizedException(NotSupportedException e) {
		String[] tokens = e.getMessage().split("-");
		ResponseDto dto = new ResponseDto(tokens[0], tokens[1]);
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * 401 코드 처리
	 */
	@ExceptionHandler(value = {UnauthorizedException.class})
	public ResponseEntity<ResponseDto> unauthorizedException(UnauthorizedException e) {
		String[] tokens = e.getMessage().split("-");
		ResponseDto dto = new ResponseDto(tokens[0], tokens[1]);
		return new ResponseEntity<ResponseDto>(dto, HttpStatus.UNAUTHORIZED);
	}

}
