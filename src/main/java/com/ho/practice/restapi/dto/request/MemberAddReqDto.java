package com.ho.practice.restapi.dto.request;

import com.ho.practice.restapi.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 등록 요청 객체
 * @author hhsung
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MemberAddReqDto {
	
	private String id;
	private String password;
	private String name;
	private String email;
	private String phoneNumber;
	private String depart;
	private String position;
	private String authCodeId;
	
	public MemberDto toMemberDto() {
		// TODO Auto-generated method stub
		return MemberDto.builder().build();
	}
    
}
