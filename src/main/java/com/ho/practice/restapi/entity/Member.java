package com.ho.practice.restapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.request.MemberUpdateReqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {
	
	@Id
	private String id;
	
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String phoneNumber;
	
	private String authCodeId;

	private String depart;
	
	private String position;
	
	@CreatedDate
	@Column(updatable = false)
    private LocalDateTime regdate;
	
	private String comments;
	
	private String regMemberId;
	
	private boolean recentStatus;
    
	public Member() {
	}

	public Member(MemberDto memberDto) {
		// TODO Auto-generated constructor stub
	}

	public MemberDto toMemberDto() {
		// TODO Auto-generated method stub
		return MemberDto.builder().build();
	}

	public void setUpdate(MemberUpdateReqDto dto) {
		// TODO Auto-generated method stub
	}
	
}
