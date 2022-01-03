# test-api-server

## Dependencies
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Data Redis

## Development
- Lombok
- Spring DevTools
- [Springdoc OpenAPI](/test/src/main/java/com/inyestar/test/config/OpenApiConfig.java)
- [Embbeded Redis](/test/src/main/java/com/inyestar/test/config/EmbeddedRedisConfig.java)

## DB
- h2 for Test
- [schema](/test/src/main/resources/schema.sql)
- [sample data](/test/src/main/resources/data.sql)

## REST APIs
- 로그인(JWT 토큰 발행)
- 로그아웃(JWT 토큰 만료)
- 유저 생성(회원 가입)
- 단일 회원 상세 정보 조회
- 단일 회원의 주문 목록 조회
- 여러 회원 목록 조회

## 인증
- /login으로 이메일과 비밀번호로 인증을 시도하면 refresh 토큰과 access 토큰 발급
- refresh 토큰은 email을 key 값으로 하여 redis 서버에 저장
- Authorization 헤더에 토큰이 있을 경우 토큰에 답긴 ROLE 정보를 기반으로 인증
- /logout 호출하면 Authorization 헤더에 담긴 토큰에서 email을 추출하여 redis 서버에 저장되어 있는 refresh 토큰 삭제
* swagger-ui 관련 URL은 인증 필요 없음
* 그 외의  모든 URL은 인증 필요
* (TODO) refresh 토큰으로 토큰 재발행은 아직 구현 안됨