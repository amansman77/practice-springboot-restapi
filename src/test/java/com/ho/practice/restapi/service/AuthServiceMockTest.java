package com.ho.practice.restapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.NotSupportedException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.util.JwtTokenUtil;

/**
 * 서비스의 인터페이스를 테스트하는 환경
 * @author hhsung
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceMockTest {
	
	@Test
	public void create_valid_refresh_TokenTest() {
		//given
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("test-secret-key");
		Long sessionTime = 3000L;
		AuthService service = new AuthService(jwtTokenUtil, sessionTime);
		
		//when
		String createToken = service.createToken("testid", "testpw");
		try {
			service.isValidToken("Bearer " + createToken);
		} catch (UnauthorizedException e) {
			assertThat(true).isFalse();
		} catch (NotSupportedException e) {
			assertThat(true).isFalse();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			assertThat(true).isFalse();
		}
		
		String refreshToken = service.refreshToken("Bearer " + createToken);
		
		//then
		assertThat(createToken).isNotNull();
		assertThat(createToken.length()).isGreaterThan(0);
		
		assertThat(refreshToken).isNotNull();
		assertThat(refreshToken.length()).isGreaterThan(0);
		
		assertThat(createToken).isNotEqualTo(refreshToken);
	}
	
	@Test(expected = UnauthorizedException.class)
	public void validTokenTest_expired_occurUnauthorizedException() throws UnauthorizedException, NotSupportedException {
		//given
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("test-secret-key");
		Long sessionTime = 1L;
		AuthService service = new AuthService(jwtTokenUtil, sessionTime);
		
		//when
		String createToken = service.createToken("testid", "testpw");
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			assertThat(true).isFalse();
		}
		
		service.isValidToken("Bearer " + createToken);
		
		//then
	}
	
	@Test(expected = UnauthorizedException.class)
	public void validTokenTest_key_occurUnauthorizedException() throws UnauthorizedException, NotSupportedException {
		//given
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("test-secret-key");
		Long sessionTime = 60*1000L;
		AuthService service = new AuthService(jwtTokenUtil, sessionTime);
		
		//when
		String createToken = service.createToken("testid", "testpw");
		service.isValidToken("Bearer " + createToken + "A");
		
		//then
	}
	
	@Test
	public void getUserIdTest() {
		//given
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("test-secret-key");
		Long sessionTime = 3000L;
		AuthService service = new AuthService(jwtTokenUtil, sessionTime);
		
		//when
		String createToken = service.createToken("testid", "testpw");
		try {
			service.isValidToken("Bearer " + createToken);
		} catch (UnauthorizedException e) {
			assertThat(true).isFalse();
		} catch (NotSupportedException e) {
			assertThat(true).isFalse();
		}
		
		String userId = service.getUserId("Bearer " + createToken);
		
		//then
		assertThat(createToken).isNotNull();
		assertThat(createToken.length()).isGreaterThan(0);
		
		assertThat(userId).isNotNull();
		assertThat(userId).isEqualTo("testid");
	}
	
}
