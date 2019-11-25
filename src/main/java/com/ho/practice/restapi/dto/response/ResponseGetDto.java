package com.ho.practice.restapi.dto.response;

import com.ho.practice.restapi.constant.ConstantResultCode.SuccessCode;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessMsg;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Get에 대한 Response를 위한 응답 객체
 * @author hhsung
 *
 */
@Getter
@ToString
public class ResponseGetDto extends ResponseDto {
	
	private Object data;
	
	@Builder(builderMethodName = "successBuilder")
    public ResponseGetDto(String code, String message, Object data) {
		super(SuccessCode.SUCCESS, SuccessMsg.SUCCESS);
        this.data = data;
    }
	
}
