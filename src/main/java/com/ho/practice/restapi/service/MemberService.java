package com.ho.practice.restapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ho.practice.restapi.constant.ConstantResultCode.AuthCode;
import com.ho.practice.restapi.constant.ConstantResultCode.AuthMsg;
import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.SearchParamDto;
import com.ho.practice.restapi.dto.request.MemberUpdateReqDto;
import com.ho.practice.restapi.entity.Member;
import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.repository.MemberRepository;


/**
 * 회원 관리 서비스
 * @author hhsung
 *
 */

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	public MemberService() {
	}
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addMember(MemberDto memberDto) {		
		memberRepository.save(new Member(memberDto));
	}

	public boolean hasMemeberId(String id) {
		long member = memberRepository.countById(id);
		if(member > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Page<Member> getMembers(SearchParamDto searchParamDto) {
		Page<Member> page;
		Pageable pageable = PageRequest.of(searchParamDto.getOffset(), searchParamDto.getLimit());
		
		if(searchParamDto.getQueryMap() == null || searchParamDto.getQueryMap().size() < 1) {
			// 쿼리가 없으면
			page = memberRepository.findAll(pageable);
		} else {
			// 쿼리가 있으면
			page = memberRepository.findMember(
					Member.builder()
						.id(Long.valueOf(searchParamDto.getQueryMap().get("id")))
						.name(searchParamDto.getQueryMap().get("name"))
						.build()
					, pageable);
		}
		
		return page;
	}
	
	public MemberDto getMember(String id) {
		
		Optional<Member> member = memberRepository.findById(id);
		if(member.isEmpty()) {
			return null;
		}
		
		return member.get().toMemberDto();	
	
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void updateMember(String memberId, MemberUpdateReqDto dto) {
		Optional<Member> targetMember = memberRepository.findById(memberId);
		
		if (targetMember.isPresent()) {
			targetMember.get().setUpdate(dto);
		}
		
		memberRepository.save(targetMember.get());
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void deleteMember(String id, String comments) {
		Optional<Member> getMember = memberRepository.findById(id);		
		getMember.get().setComments(comments);
		getMember.get().setRecentStatus(false);
	    memberRepository.save(getMember.get());
	}
	
	/**
	 * 유효한 사용자 인지 확인
	 * @param member
	 * @param loginPw 
	 * @throws UnauthorizedException
	 */
	public void isValidMember(MemberDto member, String loginPw) throws UnauthorizedException {
		if(member == null) {
			throw new UnauthorizedException(AuthCode.NOT_FOUND_ID+"-"+AuthMsg.NOT_FOUND_ID);
		}
		if(!member.getPassword().equals(loginPw)) {
			throw new UnauthorizedException(AuthCode.INVALID_PW+"-"+AuthMsg.INVALID_PW);
		}
	}

}
