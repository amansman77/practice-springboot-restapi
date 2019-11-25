package com.ho.practice.restapi;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes;

/**
 * Restdocs의 attribute를 생성하는 인터페이스
 * @author hhsung
 *
 */
public interface DocumentFormatGenerator {
	
	static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }
	static Attributes.Attribute getDateFormatRawdata() {
        return key("format").value("yyyyMMddhhmm");
    }
	
	static Attributes.Attribute getQueryFormat() {
        return key("format").value("{key}={value},{key}={value},...");
    }
	
}
