package com.ho.practice.restapi.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 쿼리의 형태를 변환해주는 파서
 * @author hhsung
 *
 */
public class QueryParser {

	/**
	 * String형 key=value,... 형식을 Map으로 변환
	 * @param query
	 * @return
	 */
	public Map<String, String> parsing(String query) {
		Map<String, String> map = new HashMap<>();
		if(StringUtils.isEmpty(query)) {
			return map;
		}
		
		String[] kvs = query.split(",");
		for (int i = 0; i < kvs.length; i++) {
			String[] kv = kvs[i].split("=");
			if(StringUtils.isEmpty(kv[0])) {
				throw new IllegalArgumentException("Key Cannot be null or empty");
			}
			if(kv.length != 2) {
				map.put(kv[0], null);
			} else {
				map.put(kv[0], kv[1]);
			}
		}
		return map;
	}

}
