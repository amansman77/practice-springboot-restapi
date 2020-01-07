package com.ho.practice.restapi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.SearchParamDto;
import com.ho.practice.restapi.dto.request.MemberAddReqDto;
import com.ho.practice.restapi.dto.request.MemberUpdateReqDto;
import com.ho.practice.restapi.dto.response.MemberRspDto;
import com.ho.practice.restapi.dto.response.ResponseGetDto;
import com.ho.practice.restapi.dto.response.ResponsePostDto;
import com.ho.practice.restapi.entity.Member;
import com.ho.practice.restapi.service.AuthService;
import com.ho.practice.restapi.service.MemberService;
import com.ho.practice.restapi.util.QueryParser;

/**
 * 회원관리를 위한 컨트롤러</br>
 * 회원 가입 및 회원관리 서비스
 * 
 * @author hhsung
 *
 */
@RestController
@RequestMapping("/members/v0.1")
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	AuthService authService;
	
	/**
	 * 회원가입
	 * 
	 * @param memberAddReqDto
	 * @return
	 */
	@PostMapping(value = "")
	public ResponseEntity<ResponsePostDto> unAuthAddMember(@RequestBody MemberAddReqDto memberAddReqDto) {

		MemberDto memberDto = memberAddReqDto.toMemberDto();
		
		memberService.addMember(memberDto);
		return new ResponseEntity<ResponsePostDto>(ResponsePostDto.builder().id(memberAddReqDto.getId()).build(),
					HttpStatus.OK);
	}

	/**
	 * Id 중복 확인
	 */
	@RequestMapping(method = {RequestMethod.HEAD}, value = "/id")
	public ResponseEntity<?> unAuthHasMemberId(@RequestParam(value="id") String id) {
		/**
		 * 아이디가 존재하면 200, 존재하지 않으면 404 반환
		 */
		boolean result = memberService.hasMemeberId(id);
		if (result == true) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 리스트 회원검색, 페이징, 검색조건 : 이름, 회원 아이디, 권한
	 * 
	 * @return
	 */
	@GetMapping(value = "")
	public ResponseEntity<ResponseGetDto> getMembers(
			@RequestParam(value = "q", required = false, defaultValue = "") String query,
			@RequestParam(required = false, defaultValue = "0") String offset,
			@RequestParam(required = false, defaultValue = "10") String limit)
			throws NumberFormatException, UnsupportedEncodingException {
		QueryParser parser = new QueryParser();
		Page<Member> page = memberService.getMembers(SearchParamDto.builder()
				.queryMap(parser.parsing(URLDecoder.decode(query, StandardCharsets.UTF_8.toString())))
				.offset(Integer.valueOf(offset))
				.limit(Integer.valueOf(limit)).build()
				);
	
		List<MemberRspDto> result = new ArrayList<>();
		for (Member member : page.getContent()) {
			MemberRspDto dto = new MemberRspDto(member);
			result.add(dto);
		}
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Total-Count", String.valueOf(page.getTotalElements()));
		
		return new ResponseEntity<ResponseGetDto>(ResponseGetDto.successBuilder().data(result).build(), headers, HttpStatus.OK);
	}

	/**
	 * 내 정보 수정 모든 필드를 갱신
	 * 
	 * @param MemberUpdateReqDto
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<String> updateMember(@PathVariable(value = "id") String id,
			@RequestBody MemberUpdateReqDto memberUpdateReqDto) {
		
		memberService.updateMember(id, memberUpdateReqDto);

		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * 회원 중지
	 * 
	 * @param memberDeleteReqDto
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteMember(@PathVariable(value = "id") String id,
			@RequestParam(value = "comments") String comments) {

		memberService.deleteMember(id, comments);

		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}
