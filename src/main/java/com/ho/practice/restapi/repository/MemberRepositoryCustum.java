package com.ho.practice.restapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ho.practice.restapi.entity.Member;

public interface MemberRepositoryCustum {
	
	Page<Member> findMember(Member param, Pageable pageable);

}
