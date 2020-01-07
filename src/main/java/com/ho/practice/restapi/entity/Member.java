package com.ho.practice.restapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.request.MemberUpdateReqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String password;
	
	private String name;
	
	private String email;
	
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
