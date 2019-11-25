package com.ho.practice.restapi.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.ho.practice.restapi.constant.ConstantResultCode.SuccessCode;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessMsg;

/**
 * ResponseGetDto Test
 * @author hhsung
 *
 */
public class ResponseGetDtoTest {
	
	@Test
	public void successBuilderTest() {
		//given
		
		//when
		ResponseGetDto dto = ResponseGetDto.successBuilder().data("accesstoken").build();
		
		//then
		assertThat(dto).isNotNull();
		assertThat(dto.getCode()).isNotNull();
		assertThat(dto.getMessage()).isNotNull();
		assertThat(dto.getCode()).isEqualTo(SuccessCode.SUCCESS);
		assertThat(dto.getMessage()).isEqualTo(SuccessMsg.SUCCESS);
		assertThat(dto.getData()).isEqualTo("accesstoken");
	}
	
}
