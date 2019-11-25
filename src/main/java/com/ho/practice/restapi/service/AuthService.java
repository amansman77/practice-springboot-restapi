package com.ho.practice.restapi.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ho.practice.restapi.constant.ConstantResultCode.AuthCode;
import com.ho.practice.restapi.constant.ConstantResultCode.AuthMsg;
import com.ho.practice.restapi.constant.ConstantResultCode.NotSupportCode;
import com.ho.practice.restapi.constant.ConstantResultCode.NotSupportMsg;
import com.ho.practice.restapi.exception.UnauthorizedException;
import com.ho.practice.restapi.util.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * 권한 정보를 관리한는 서비스
 * @author hhsung
 *
 */
@Service
public class AuthService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${auth.session-time}")
	private Long sessionTime;
	
	private static final String AUTH_TYPE = "Bearer";
	private static final String CLAIMS_USER_ID = "userId";
	private static final String CLAIMS_USER_AUTH = "userAuth";
	
	public AuthService() {
	}
	
	public AuthService(JwtTokenUtil jwtTokenUtil, Long sessionTime) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.sessionTime = sessionTime;
	}

	/**
	 * 토큰 생성
	 * @param id
	 * @param password
	 * @return
	 * @throws UnauthorizedException
	 */
	public String createToken(String userId, String userAuth) {
		Map<String, Object> map = new HashMap<>();
		map.put(CLAIMS_USER_ID, userId);
		map.put(CLAIMS_USER_AUTH, userAuth);
		
		return jwtTokenUtil.generateToken(map, sessionTime);
	}

	/**
	 * 토큰 재발급
	 * @param authorization
	 * @return
	 */
	public String refreshToken(String authorization) {
		
		String token = this.getToken(authorization);
		
		Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
		String userId = (String) claims.get(CLAIMS_USER_ID);
		String userAuth = (String) claims.get(CLAIMS_USER_AUTH);
		
		Map<String, Object> map = new HashMap<>();
		map.put(CLAIMS_USER_ID, userId);
		map.put(CLAIMS_USER_AUTH, userAuth);
		
		return jwtTokenUtil.generateToken(map, sessionTime);
	}

	/**
	 * 토큰 유효성 확인
	 * @param authorization
	 * @throws UnauthorizedException
	 * @throws NotSupportedException
	 */
	public void isValidToken(String authorization) throws UnauthorizedException, NotSupportedException {
		if(authorization == null) {
	    	throw new UnauthorizedException(AuthCode.NOT_EXIST+"-"+AuthMsg.NOT_EXIST);
	    }
	    
	    String[] split = authorization.split(" ");
        String type = split[0];
        String credential = split[1];
        
        if (AUTH_TYPE.equalsIgnoreCase(type)) {
        	// 유효성 확인
        	try {
        		jwtTokenUtil.validateToken(credential);
            } catch (Exception e) {
            	if(e instanceof ExpiredJwtException) {
            		throw new UnauthorizedException(AuthCode.EXPIRED+"-"+AuthMsg.EXPIRED);
            	} else {
            		throw new UnauthorizedException(AuthCode.INVALID_TOKEN+"-"+AuthMsg.INVALID_TOKEN);
            	}
            }
        } else {
        	throw new NotSupportedException(NotSupportCode.AUTH_TYPE+"-"+NotSupportMsg.AUTH_TYPE);
        }
	}

	/**
	 * 토큰에서 사용자 아이디 반환
	 * @param authorization
	 * @return
	 */
	public String getUserId(String authorization) {
		String token = this.getToken(authorization);
		
		return (String) jwtTokenUtil.getAllClaimsFromToken(token).get(CLAIMS_USER_ID);
	}

	/**
	 * Authorization 필드에서 token 추출
	 * @param authorization
	 * @return
	 */
	private String getToken(String authorization) {
		return authorization.replace(AUTH_TYPE, "").trim();
	}
	
}
