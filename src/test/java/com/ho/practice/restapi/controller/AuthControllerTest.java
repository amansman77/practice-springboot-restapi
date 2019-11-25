package com.ho.practice.restapi.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessCode;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessMsg;
import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.request.LoginReqDto;
import com.ho.practice.restapi.service.AuthService;
import com.ho.practice.restapi.service.MemberService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	private RestDocumentationResultHandler document;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	AuthService authService;
	
	@MockBean
	private MemberService memberService;
	
	private static final String serviceUrl ="/auths/v0.1";
	
	@Before
	public void setUp() {
		this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document)
				.build();
	}
	
	@Test
    public void testLogin() throws Exception {
        //given
		String userId = "admin";
		String userPw = "admin1";
		String authCodeId = "1";
		
		given(memberService.getMember(userId)).willReturn(
				MemberDto.builder()
				.id(userId)
				.password(userPw)
				.authCodeId(authCodeId)
				.build()
				);
		given(
				authService.createToken(userId, authCodeId)
				).willReturn("accesstoken");
		
        //when
        ResultActions actions = mockMvc.perform(post(serviceUrl)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(objectMapper.writeValueAsString(
	                		LoginReqDto.builder().id(userId).password(userPw).build()
	                		))
                )
                .andDo(print());
        
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(SuccessCode.SUCCESS))
                .andExpect(jsonPath("$.message").value(SuccessMsg.SUCCESS))
                .andExpect(jsonPath("$.data").value("accesstoken"))
                .andDo(this.document.document(
                		requestFields(
                				fieldWithPath("id").description("회원 아이디"),
                				fieldWithPath("password").description("회원 비밀번호")
                		),
                		responseFields(
    							fieldWithPath("code").description("응답 코드"),
    							fieldWithPath("message").description("응답 메세지"),
    							fieldWithPath("data").description("응답 데이터, accesstoken이 string 형태로 들어있음")
                		)
                		)
                )
                ;
    }
	
	@Test
    public void testLogin_noAuthCodeId() throws Exception {
        //given
		String userId = "admin";
		String userPw = "admin1";
		
		given(memberService.getMember(userId)).willReturn(
				MemberDto.builder()
				.id(userId)
				.password(userPw)
				.build()
				);
		given(
				authService.createToken(userId, null)
				).willReturn("accesstoken");
		
        //when
        ResultActions actions = mockMvc.perform(post(serviceUrl)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(objectMapper.writeValueAsString(
	                		LoginReqDto.builder().id(userId).password(userPw).build()
	                		))
                )
                .andDo(print());
        
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("accesstoken"))
                ;
    }
	
	@Test
    public void testRefreshToken() throws Exception {
        //given
		String authorization = "Bearer test-token";
		
		given(
				authService.refreshToken(authorization)
				).willReturn("accesstoken");
		
        //when
        ResultActions actions = mockMvc.perform(put(serviceUrl)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .header("Authorization", authorization)
                )
                .andDo(print());
        
        //then
        actions
                .andExpect(status().isOk())
                .andDo(this.document.document(
                		responseFields(
                				fieldWithPath("code").description("응답 코드"),
    							fieldWithPath("message").description("응답 메세지"),
    							fieldWithPath("data").description("응답 데이터, accesstoken이 string 형태로 들어있음")
                		)
                		)
                )
                ;
    }
	
}
