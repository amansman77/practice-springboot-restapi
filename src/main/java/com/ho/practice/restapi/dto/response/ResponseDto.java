package com.ho.practice.restapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Response를 위한 응답 객체
 * @author hhsung
 *
 */
@Getter
@AllArgsConstructor
@Builder
public class ResponseDto {
	
	private String code;
	private String message;
	
}
