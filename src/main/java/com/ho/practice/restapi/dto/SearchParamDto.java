package com.ho.practice.restapi.dto;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 검색 조건을 가지는 Dto
 * @author hhsung
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchParamDto {
	
	private Map<String, String> queryMap;
	private int offset;
	private int limit;
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		SearchParamDto dto = (SearchParamDto) o;
		
		if(queryMap != null) {
			Set<Entry<String, String>> entries = queryMap.entrySet();
			for (Entry<String, String> entry : entries) {
				if(!entry.getValue().equals(dto.queryMap.get(entry.getKey()))) {
					return false;
				}
			}
		}
		return offset == dto.offset &&
				limit == dto.limit;
	}
	
}
