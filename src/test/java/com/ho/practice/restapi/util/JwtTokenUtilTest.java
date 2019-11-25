package com.ho.practice.restapi.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenUtilTest {

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Test
	public void generateTokenTest() {
		//given
		
		//when
		Map<String, Object> map = new HashMap<>();
		map.put("userId", "testid");
		map.put("userAuth", null);
		
		String token = jwtTokenUtil.generateToken(map, 1000);
		
		String userId = (String) jwtTokenUtil.getAllClaimsFromToken(token).get("userId");
		
		//then
		assertThat(token).isNotNull();
		assertThat(token.length()).isGreaterThan(0);
		
		assertThat(userId).isEqualTo("testid");
		
	}
	
	@Test(expected=SignatureException.class)
	public void validateTokenTest_SignatureException() {
		//given
		
		//when
		Map<String, Object> map = new HashMap<>();
		map.put("userId", "testid");
		map.put("userAuth", null);
		
		String token = jwtTokenUtil.generateToken(map, 1000);
		
		Jwts.parser().setSigningKey("test-secret").parseClaimsJws(token).getBody();
		
		//then
		
	}
	
	@Test(expected=ExpiredJwtException.class)
	public void validateTokenTest_ExpiredJwtException() {
		//given
		
		//when
		Map<String, Object> map = new HashMap<>();
		map.put("userId", "testid");
		map.put("userAuth", null);
		
		String token = jwtTokenUtil.generateToken(map, 1);
		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
		}
		
		jwtTokenUtil.getAllClaimsFromToken(token);
		
		//then
		
	}
	
}
