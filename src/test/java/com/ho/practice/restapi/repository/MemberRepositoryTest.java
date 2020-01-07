package com.ho.practice.restapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ho.practice.restapi.config.QuerydslConfigurationTest;
import com.ho.practice.restapi.entity.Member;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {MemberRepository.class}
))
@ContextConfiguration(classes = {QuerydslConfigurationTest.class})
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Before
	public void setData() {
		List<Member> testData = 
				  Arrays.asList(Member.builder().id(0L).build());
		memberRepository.saveAll(testData);
	}
	
	@Test
	public void 모든_회원_조회() {
		Page<Member> findMemberList = memberRepository.findMember(
				Member.builder().build()
				, PageRequest.of(0, 3)
				);
		
		assertThat(findMemberList.getContent().size()).isEqualTo(1);
	}
	
}
