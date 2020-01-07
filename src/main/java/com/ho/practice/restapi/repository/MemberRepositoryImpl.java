package com.ho.practice.restapi.repository;

import static com.ho.practice.restapi.entity.QMember.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.ho.practice.restapi.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustum {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public Page<Member> findMember(Member param, Pageable pageable) {
		
		BooleanBuilder builder = new BooleanBuilder();

        if (param.getId() != null) {
            builder.and(member.id.eq(param.getId()));
        }
        if (!StringUtils.isEmpty(param.getName())) {
            builder.and(member.name.eq(param.getName()));
        }
        
        QueryResults<Member> result = queryFactory
				.selectFrom(member)
				.where(builder)
				.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}
	

}
