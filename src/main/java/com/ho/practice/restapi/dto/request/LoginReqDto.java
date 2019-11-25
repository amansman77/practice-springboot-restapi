package com.ho.practice.restapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 로그인의 요청 객체
 * @author hhsung
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
	private String id;
	private String password;
}
