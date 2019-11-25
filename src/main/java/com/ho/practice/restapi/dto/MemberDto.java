package com.ho.practice.restapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 객체
 * @author hhsung
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
	
	private String id;
	private String password;
	private String name;
	private String email;
	private String phoneNumber;
	private String depart;
	private String position;
	private String authCodeId;
	private String authCodeName;
	private String comments;
	private LocalDateTime regdate;
	private String regMemberId;
	private boolean recentStatus;
	
}
