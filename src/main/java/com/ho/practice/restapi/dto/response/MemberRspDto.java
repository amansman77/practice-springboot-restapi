package com.ho.practice.restapi.dto.response;

import java.time.LocalDateTime;

import com.ho.practice.restapi.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 정보 반환 객체
 * @author hhsung
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRspDto {
	
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	private String depart;
	private String position;
	private String authCodeName;
	private String authCodeId;
    private LocalDateTime regdate;
	private boolean recentStatus;
	private String comments;
	
	public MemberRspDto(Member member) {
		// TODO Auto-generated constructor stub
	}
	
}
