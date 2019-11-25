# Spring REST Docs 활용

## 목표
Springboot기반으로 RestAPI 설계를 합니다.


## 개발 프레임워크
 - IDE : STS-4.2.2.RELEASE
 - Java : openjdk 12.0.1
 - Spring Boot : 2.1.8
 - Gradle : 5.6.2
 
 
## Lombok 활용

객체의 상속 상황에서 활용도 있음

## 데이터 연결

### JPA 활용

### QueryDSL 활용

조건절을 동적으로 활용하기 위해서

### AuditingEntityListener

등록일과 같은 시스템에서 채워주는 성향의 필드에 대해서 프레임워크에서 채우도록 설정


## Advice 활용

### Exception별 오류응답 처리


## Aspect 활용

### API 로그 생성

### JWT 유효성 확인

`controller` 패키지 내에 `unAuth`를 prefix로 가진 함수는 유효성을 확인하지 않음


## 인증

JWT 활용


## CORS 설정

`WebMvcConfigurer`를 활용하여 허용 URL 및 Method, Header 설정

관련 Resource 도 설정


## API 스타일

- API URI를 `/[component]/[버전]` 형태로 구성
- `RequestMethod.HEAD`를 활용하여 resource의 존재유무 확인
- 응답 헤더 `X-Total-Count`에 응답 결과의 총 수 반환


## API 공유

ASCII Doctor를 활용하여 인터페이스 공유
문서 경로 : [프로젝트 Path]/build/asciidoc/html5/index.html

## 참고 문헌
 - RFC : [HTTP](https://tools.ietf.org/html/rfc7231), [GET](https://tools.ietf.org/html/rfc7231#section-4.3.1), [HEAD](https://tools.ietf.org/html/rfc7231#section-4.3.2), [POST](https://tools.ietf.org/html/rfc7231#section-4.3.3), [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4), [DELETE](https://tools.ietf.org/html/rfc7231#section-4.3.5)
