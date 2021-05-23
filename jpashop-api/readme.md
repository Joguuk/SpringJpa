# API 설계 주의
1. API 스펙에 절대 엔티티를 노출하지 말 것.
 - 별도의 DTO를 만들어서 API를 설계
 - 엔티티를 노출하면 엔티티가 변경되는 순간 API 스펙 자체가 변경되는 문제
 - DTO를 만들게 되면 API request에 필요한 값들만 정의해서 개발자들간의 혼선을 줄일 수 있음.

2. "API 스펙에 맞는 DTO 생성을 권장이 아닌 강제합니다"

# OSIV(Open Session In View: 하이버네이트)
- Open EntityManager In View: JPA(관레상 OSIV라 한다.)
- spring.jpa.open-in-view: true(기본값)
- OSIV 전략은 트랜잭션 시작처럼 최초 데이터베이스 커넥션 시작 시점부터 API 응답이 끝날 때 까지 영속성 컨텍스트와 데이터베이스 커넥션을 유지한다.
- 지연 로딩은 영속성 컨텍스트가 살아있어야 가능하고, 영속성 컨텍스트는 기본적으로 데이터베이스 커넥션을 유지한다. 이것 자체는 큰 장점이다.
- 그런데 이 전략은 너무 오랜시간동안 DB 커넥션 리소스를 사용하기 때문에, 실시간 트래픽이 중요한 어플리케이션은 커넥션이 모자랄 수 있고 최악의 경우 장애로 이어질 수 있다.

---
# Refrence  
https://www.inflearn.com/course/스프링부트-JPA-API개발-성능최적화/dashboard
