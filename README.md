# test-api-server

## Dependencies
- Spring Web
- Spring Security
- Spring JPA

## Development
- Lombok
- Spring DevTools

## DB
- vendor : h2
- [schema](/test/src/main/resources/schema.sql)
- [sample data](/test/src/main/resources/data.sql)

## Rest APIs
- 로그인(JWT 토큰 발행)
- 회어 가입(유저 생성)
- 단일 회원 상세 정보 조회
- 단일 회원의 주문 목록 조회
- 여러 회원 목록 조회