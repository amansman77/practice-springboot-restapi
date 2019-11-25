package com.ho.practice.restapi.service;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.entity.Member;
import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.repository.MemberRepository;

/**
 * 서비스의 인터페이스를 테스트하는 환경
 * @author hhsung
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceMockTest {
	
	@Test
	public void isValidMemberTest() throws UnauthorizedException {
		//given
		MemberService service = new MemberService();
		
		//when
		service.isValidMember(
				MemberDto.builder().password("testpw").build()
				, "testpw");
		
		//then
	}
	
	@Test(expected = UnauthorizedException.class)
	public void isValidMemberTest_notFoundId() throws UnauthorizedException {
		//given
		MemberService service = new MemberService();
		
		//when
		service.isValidMember(null, null);
		
		//then
	}
	
	@Test(expected = UnauthorizedException.class)
	public void isValidMemberTest_invalidPw() throws UnauthorizedException {
		//given
		MemberService service = new MemberService();
		
		//when
		service.isValidMember(
				MemberDto.builder().password("testpw").build()
				, "test");
		
		//then
	}
	
	static class MockMemberRepository implements MemberRepository {

		@Override
		public List<Member> findAll() {
			return null;
		}

		@Override
		public List<Member> findAll(Sort sort) {
			return null;
		}

		@Override
		public List<Member> findAllById(Iterable<String> ids) {
			return null;
		}

		@Override
		public <S extends Member> List<S> saveAll(Iterable<S> entities) {
			return null;
		}

		@Override
		public void flush() {
		}

		@Override
		public <S extends Member> S saveAndFlush(S entity) {
			return null;
		}

		@Override
		public void deleteInBatch(Iterable<Member> entities) {
		}

		@Override
		public void deleteAllInBatch() {
		}

		@Override
		public Member getOne(String id) {
			return null;
		}

		@Override
		public <S extends Member> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Member> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public Page<Member> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Member> S save(S entity) {
			return null;
		}

		@Override
		public Optional<Member> findById(String id) {
			return null;
		}

		@Override
		public boolean existsById(String id) {
			return false;
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void deleteById(String id) {
		}

		@Override
		public void delete(Member entity) {
		}

		@Override
		public void deleteAll(Iterable<? extends Member> entities) {
		}

		@Override
		public void deleteAll() {
		}

		@Override
		public <S extends Member> Optional<S> findOne(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Member> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends Member> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public Page<Member> findMember(Member param, Pageable pageable) {
			return null;
		}

		@Override
		public long countById(String id) {
			return 0;
		}
	}
	
}
