package com.ho.practice.restapi.exception;

/**
 * 권한에 대한 exception 처리
 * @author hhsung
 *
 */
public class UnauthorizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7545169112228181351L;
	
	public UnauthorizedException(String errorCode) {
		super(errorCode);
	}

}
