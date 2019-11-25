package com.ho.practice.restapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Post에 대한 Response를 위한 응답 객체
 * @author hhsung
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDto {
	
	private String id;
	
}
