package com.ho.practice.restapi.util;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT를 활용하는 유틸
 * @author hhsung
 *
 */
@Component
public class JwtTokenUtil {

	@Value("${auth.secret-key}")
	private String secret;

	public JwtTokenUtil() {
	}
	public JwtTokenUtil(String secret) {
		this.secret = secret;
	}

	public String generateToken(Map<String, Object> claims, long validCycle) {
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.setExpiration(new Date(System.currentTimeMillis() + validCycle))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.compact();
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public void validateToken(String token) {
		Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	}

}
