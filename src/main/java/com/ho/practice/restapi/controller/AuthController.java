package com.ho.practice.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.request.LoginReqDto;
import com.ho.practice.restapi.dto.response.ResponseGetDto;
import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.service.AuthService;
import com.ho.practice.restapi.service.MemberService;

/**
 * 사용자 인증 및 권한 관리를 위한 컨트롤러
 * 
 * @author hhsung
 *
 */
@RestController
@RequestMapping("/auths/v0.1")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 로그인
	 * access token 발급
	 * @return
	 * @throws UnauthorizedException 
	 */
	@PostMapping(value = "")
	public ResponseGetDto unAuthLogin(@RequestBody LoginReqDto loginReqDto) throws UnauthorizedException {
		// 사용자 유효성 확인
		MemberDto member = memberService.getMember(loginReqDto.getId());
		memberService.isValidMember(member, loginReqDto.getPassword());
		
		// 토큰 발급
		String accesstoken = authService.createToken(member.getId(), member.getAuthCodeId());
		
		return ResponseGetDto.successBuilder().data(accesstoken).build();
	}
	
	/**
	 * 토큰갱신
	 * @return
	 */
	@PutMapping(value = "")
	public ResponseGetDto refreshToken(
			@RequestHeader(value = "Authorization") String authorization
			) {
		
		String accesstoken = authService.refreshToken(authorization);
		
		return ResponseGetDto.successBuilder().data(accesstoken).build();
	}
	
}
