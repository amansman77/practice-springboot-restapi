package com.ho.practice.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ho.practice.restapi.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustum {

	long countById(String id);
	
}
