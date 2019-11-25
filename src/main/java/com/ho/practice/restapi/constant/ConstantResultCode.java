package com.ho.practice.restapi.constant;

/**
 * API의 결과 코드 관리
 * @author hhsung
 *
 */
public interface ConstantResultCode {
	// 2000 번대
	public interface SuccessCode {
		public static final String SUCCESS = "2000";
	}
	public interface SuccessMsg {
		public static final String SUCCESS = "정상처리됐습니다.";
	}
	
	// 4010 번대
	public interface AuthCode {
		public static final String NOT_EXIST = "4011";
		public static final String EXPIRED = "4012";
		public static final String INVALID_TOKEN = "4013";
		public static final String NOT_FOUND_ID = "4014";
		public static final String INVALID_PW = "4015";
	}
	public interface AuthMsg {
		public static final String NOT_EXIST = "토큰이 존재하지 않습니다.";
		public static final String EXPIRED = "기간이 만료됐습니다.";
		public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";
		public static final String NOT_FOUND_ID = "아이디가 존재하지 않습니다.";
		public static final String INVALID_PW = "비밀번호가 일치하지 않습니다.";
	}
	
	// 5000 번대
	public interface NotSupportCode {
		public static final String AUTH_TYPE = "5001";
		public static final String MODEL_SUBDATA_TYPE = "5002";
	}
	public interface NotSupportMsg {
		public static final String AUTH_TYPE = "지원하지 않는 권한 타입입니다.";
		public static final String MODEL_SUBDATA_TYPE = "지원하지 않는 모델의 서브데이터 타입입니다.";
	}
		
}
