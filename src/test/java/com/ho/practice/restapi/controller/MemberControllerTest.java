package com.ho.practice.restapi.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ho.practice.restapi.DocumentFormatGenerator;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessCode;
import com.ho.practice.restapi.constant.ConstantResultCode.SuccessMsg;
import com.ho.practice.restapi.dto.MemberDto;
import com.ho.practice.restapi.dto.SearchParamDto;
import com.ho.practice.restapi.dto.request.MemberAddReqDto;
import com.ho.practice.restapi.dto.request.MemberUpdateReqDto;
import com.ho.practice.restapi.entity.Member;
import com.ho.practice.restapi.service.MemberService;
import com.ho.practice.restapi.util.QueryParser;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	private RestDocumentationResultHandler document;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private MemberService mamberService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

	private static final String serviceUrl ="/members/v0.1";
	
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
    public void testAddMember() throws Exception {
        //given
		MemberAddReqDto memberReqDto = MemberAddReqDto.builder()
			.id("admin")
			.password("1234")
			.name("홍길동")
			.email("hong@micube.co.kr")
			.phoneNumber("010-0000-0000")
			.depart("관리부")
			.position("부장")
			.authCodeId("1")
			.build();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(memberReqDto.getId());
		memberDto.setPassword(memberReqDto.getPassword());
		memberDto.setName(memberReqDto.getName());
		memberDto.setEmail(memberReqDto.getEmail());
		memberDto.setPhoneNumber(memberReqDto.getPhoneNumber());
		memberDto.setDepart(memberReqDto.getDepart());
		memberDto.setPosition(memberReqDto.getPosition());
		memberDto.setAuthCodeId(memberReqDto.getAuthCodeId());
		
		
        //when
        ResultActions actions = mockMvc.perform(post(serviceUrl)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(objectMapper.writeValueAsString(memberReqDto))
                )
                .andDo(print());
        
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("admin"))
                .andDo(this.document.document(
                		requestFields(
                				fieldWithPath("id").description("회원 아이디, 사번"),
                				fieldWithPath("password").description("회원 비밀번호"),
                				fieldWithPath("name").description("회원 이름"),
                				fieldWithPath("email").description("회원 이메일"),
                				fieldWithPath("phoneNumber").description("회원 전화번호"),
                				fieldWithPath("depart").description("회원 부서코드").optional(),
                				fieldWithPath("position").description("회원 직책코드").optional(),
                				fieldWithPath("authCodeId").description("권한 코드 ID").optional()
                		),
                		responseFields(
    							fieldWithPath("id").description("멤버 아이디")
                		)
                		)
                )
                ;
	
	
    }
	
	@Test
  public void testHasMemberId() throws Exception {
	//given
	String id = "admin";
	
	given(mamberService.hasMemeberId(id)).willReturn(false);
      //when
      ResultActions testActions = mockMvc.perform(head(serviceUrl+"/id?id=admin" + URLEncoder.encode("id=admin", StandardCharsets.UTF_8.toString()))
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
              )
              .andDo(print());
      
      //then
      testActions
              .andExpect(status().isNotFound())
              .andDo(this.document.document(
              		requestParameters(
  							parameterWithName("id").description("중복확인 할 회원 ID")
  					))
              )
              ;
  }
	
	@Test
	public void testGetMembers() throws Exception {
		//given
		String query = "authCodeId=1,name=홍길동";
		int offset = 0;
		int limit = 2;
		QueryParser parser = new QueryParser();
		Pageable pageable = PageRequest.of(offset, limit);
		SearchParamDto searchParamDto = SearchParamDto.builder()
				.queryMap(parser.parsing(query))
				.offset(Integer.valueOf(offset))
				.limit(Integer.valueOf(limit))
				.build();
		
		given(mamberService.getMembers(searchParamDto)).willReturn(new PageImpl<>(Arrays.asList(
				Member.builder()
				  		.id("admin")
						.name("홍길동")
						.email("hong@micube.co.kr")
						.phoneNumber("010-1111-2222")
						.depart("관리")
						.position("부서장")
						.recentStatus(true)
						.regdate(LocalDateTime.now())
						.authCodeId("11")
						.comments("")
					.build()
				  ,  Member.builder()
				  		.id("worker")
						.name("김철수")
						.email("jhon@micube.co.kr")
						.phoneNumber("010-2222-8282")
						.depart("스트렌드1호기")
						.position("작업자")
						.recentStatus(false)
						.regdate(LocalDateTime.now())
						.authCodeId("12")
						.comments("퇴사로 인한 삭제")
				.build()
				), pageable, 10));
		
		//when
		final ResultActions actions = mockMvc.perform(get(serviceUrl+"?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
															+ "&offset="+offset+"&limit="+limit)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
	
				)
				.andDo(print());
		
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(header().string("X-Total-Count", "10"))
			.andExpect(jsonPath("$.code").value(SuccessCode.SUCCESS))
            .andExpect(jsonPath("$.message").value(SuccessMsg.SUCCESS))
			.andExpect(jsonPath("$.data", IsCollectionWithSize.hasSize(2)))
			.andExpect(jsonPath("$.data[0].id").value("admin"))
			.andExpect(jsonPath("$.data[0].name").value("홍길동"))
			.andExpect(jsonPath("$.data[0].email").value("hong@micube.co.kr"))
			.andDo(this.document.document(
					requestParameters(
							parameterWithName("q").description("조회 쿼리, 가능 필드={id, name, authCodeId}").attributes(DocumentFormatGenerator.getQueryFormat()),
							parameterWithName("offset").description("쿼리 오프셋"),
							parameterWithName("limit").description("쿼리 리미트")
					),
					responseHeaders(
							headerWithName("X-Total-Count").description("총 결과 수")
					),
					 responseFields(
							fieldWithPath("code").description("응답 코드"),
							fieldWithPath("message").description("응답 메세지"),
							fieldWithPath("data").description("데이터"),
							fieldWithPath("data.[].id").description("회원 아이디 / 사번"),
	         				fieldWithPath("data.[].name").description("회원 이름"),
	         				fieldWithPath("data.[].email").description("회원 이메일"),
	         				fieldWithPath("data.[].phoneNumber").description("회원 전화번호"),
	         				fieldWithPath("data.[].recentStatus").description("회원 현재상태"),
	         				fieldWithPath("data.[].comments").description("회원 중지사유(비고)"),    		
	         				fieldWithPath("data.[].authCodeName").description("권한 명"),
	         				fieldWithPath("data.[].authCodeId").description("권한 아이디"),
	         				fieldWithPath("data.[].depart").description("부서"),
	         				fieldWithPath("data.[].position").description("직책"),
	         				fieldWithPath("data.[].regdate").description("등록일")
	        		)
					)
			)
		;
	}

	@Test
	public void testUpdateMember() throws Exception {
		//given
		
		//when
		final ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.put(serviceUrl+"/{id}", "member-id")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(
							MemberUpdateReqDto.builder()
	                		.name("홍길동")
	                		.email("hong@micube.co.kr")
	                		.phoneNumber("010-0000-0000")
	                		.depart("관리부")
	                		.position("부장")
	                		.authCodeId("1")
	                		.build()
	                		))
				)
				.andDo(print());
		
		//then
		actions
		.andExpect(status().isOk())
		.andDo(this.document.document(
				pathParameters(
	                    parameterWithName("id").description("회원 ID")
				),
					requestFields(
	        				fieldWithPath("name").description("회원 이름"),
	        				fieldWithPath("email").description("회원 이메일"),
	        				fieldWithPath("phoneNumber").description("회원 전화번호"),
	        				fieldWithPath("depart").description("회원 부서코드"),
	        				fieldWithPath("position").description("회원 직책코드"),
	        				fieldWithPath("authCodeId").description("권한 아이디")			
	        		))
			)
		;
	}
	
	@Test
	public void testDeleteMember() throws Exception {
		//given
		
		//when
		final ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.delete(serviceUrl+"/{id}?comments=퇴사로 인한 삭제", "member-id")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
	
				)
				.andDo(print());
		
		//then
		actions
			.andExpect(status().isOk())
			.andDo(this.document.document(
					pathParameters(
	                        parameterWithName("id").description("회원 아이디")
					),
					requestParameters(
							parameterWithName("comments").description("중지 사유")
	        				
	        		))
			)
		;
	}

}