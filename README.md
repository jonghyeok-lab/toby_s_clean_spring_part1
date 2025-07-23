# What should I remember from what I have Learned?
---

#### Spring Data
1. Spring Data를 쓰는 목적은 추상화에 있다.
    - JPA는 save를 호출하지 않더라도 변경감지를 통해 바뀐 값을 DB에 반영한다.
    - 하지만 우리는 Spring Data를 사용하며, Spring Data 는 save(), findById()와 같이 메서드를 추상화하여 RDB가 NoSQL로 바뀌어도 Spring Data 가 똑같은 메서드를 제공해서 변경의 여파를 줄여준다.
2. Spring Data는 Event Publication / Auditing 을 제공한다.
    - update 시 에도 save 를 꼭 호출해주자.

#### 테스트 코드
- MockMvcTester 사용
- 중복되는 코드를 MemberFixture 처럼 Util 로 빼자
- 테스트의 @Transactional 은 Spring 에서 강하게 지원하는 기능 중 하나
- `adapter.webapi.MemberApiTest` 가 API 기능 테스트 클래스, `MemberApiWebMvcTest`는 웹MVC만 슬라이스 테스트

#### ProblemDetail 적극 사용

#### 엔티티에 도메인 모델을 투영
- JpaBuddy 플러그인 추가해서 EqualsAndHashcode 를 재정의해야 LazyLoading의 프록시 객체와 실제 인스턴스 간의 비교가 된다.
- `Objects.requireNonNull`(런타임) + SpotBugs(정적도구) 를 통해 null 예방하기

#### 아키텍처
- 계층형 아키텍처도 원래는 레이어를 건너뛰어도 된다. -> 완화된 계층형 아키텍처
- 도메인(엔티티)를 웹 계층에 반환해도 괜찮다. 어그리거트로 접근해야 하기 때문
- 통계성 DTO 도 MemberRegisterRequest 도 사실상 도메인 개념으로 보는 것이 맞다.
- `src.main.java.learn` 에 도메인모델.md/기타.md/애그리거트.md/헥사고날아키텍처.md 개념 정리

#### docker compose
- `developmentOnly("org.springframework.boot:spring-boot-docker-compose")` 의존성 주입
- compose.yaml 에 docker 컨테이너 정의
```angular2html
spring:
  docker:
    compose:
      lifecycle-management: start_only
```
를 통해 처음 애플리케이션 시작할 때만 docker 컨테이너를 띄운다.


