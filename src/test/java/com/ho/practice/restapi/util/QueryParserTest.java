package com.ho.practice.restapi.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void parsingTest() {
		//given
		String case1 = "";
		String case2 = "key1=value1";
		String case3 = "key1=value1,key2=value2";
		String case4 = "key1=";
		String case5 = "key1=,key2=value2";
		String case6 = "key1==";
		String case7 = "key1==value1";
		
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		resultMap2.put("key1", "value1");
		Map<String, String> resultMap3 = new HashMap<String, String>();
		resultMap3.put("key1", "value1");
		resultMap3.put("key2", "value2");
		Map<String, String> resultMap4 = new HashMap<String, String>();
		resultMap4.put("key1", null);
		Map<String, String> resultMap5 = new HashMap<String, String>();
		resultMap5.put("key1", null);
		resultMap5.put("key2", "value2");
		Map<String, String> resultMap6 = new HashMap<String, String>();
		resultMap6.put("key1", null);
		Map<String, String> resultMap7 = new HashMap<String, String>();
		resultMap7.put("key1", null);
		
		QueryParser parser = new QueryParser();
		
		//when
		Map<String, String> queryMap1 = parser.parsing(case1);
		Map<String, String> queryMap2 = parser.parsing(case2);
		Map<String, String> queryMap3 = parser.parsing(case3);
		Map<String, String> queryMap4 = parser.parsing(case4);
		Map<String, String> queryMap5 = parser.parsing(case5);
		Map<String, String> queryMap6 = parser.parsing(case6);
		Map<String, String> queryMap7 = parser.parsing(case7);
		
		//then
		assertThat(queryMap1).isEqualTo(resultMap1);
		assertThat(queryMap1.size()).isEqualTo(resultMap1.size());
		assertThat(queryMap2).isEqualTo(resultMap2);
		assertThat(queryMap2.size()).isEqualTo(resultMap2.size());
		assertThat(queryMap3).isEqualTo(resultMap3);
		assertThat(queryMap3.size()).isEqualTo(resultMap3.size());
		assertThat(queryMap4).isEqualTo(resultMap4);
		assertThat(queryMap4.size()).isEqualTo(resultMap4.size());
		assertThat(queryMap5).isEqualTo(resultMap5);
		assertThat(queryMap5.size()).isEqualTo(resultMap5.size());
		assertThat(queryMap6).isEqualTo(resultMap6);
		assertThat(queryMap6.size()).isEqualTo(resultMap6.size());
		assertThat(queryMap7).isEqualTo(resultMap7);
		assertThat(queryMap7.size()).isEqualTo(resultMap7.size());
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void parsingTestIllegalArgumentException() throws IllegalArgumentException {
		//given
		QueryParser parser = new QueryParser();
		
		//when
		parser.parsing("=value1");
	}
	
}
