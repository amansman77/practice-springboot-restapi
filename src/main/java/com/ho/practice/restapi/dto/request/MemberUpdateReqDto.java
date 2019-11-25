package com.ho.practice.restapi.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 수정 요청 객체
 * @author hhsung
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateReqDto {
	
	private String name;
	private String email;
	private String phoneNumber;
	private String depart;
	private String position;
	private String authCodeId;
	
}
