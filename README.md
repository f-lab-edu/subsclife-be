# 제 1회 F-THON 대상!
### 갓생을 구독하자! 구독 기반 성장 플랫폼 SubscLife

#### 관련 링크
[프로젝트 발표 자료 - 링크 준비중]()

[우리가 개발한 방식 - Youtube 영상](https://www.youtube.com/watch?v=-OXyuFoBQ9g)



### 프로젝트 구성
- Java 17
- SpringBoot 3.3.3
- Spring Data JPA + QueryDSL
- MySQL 8.0
- Naver Cloud Platform micro

### ERD
![image](https://github.com/user-attachments/assets/b1df66d0-0726-41f0-8de5-47c1ab907ba6)

### 개발 시 신경쓴 것들
#### 0. MVP(Minimum Viable Product)
아이디어를 짧은 시간동안 높은 완성도로 구체화하기 위해 최소한의 기능을 구현하는데에 집중했습니다.

1. 인증 로직 간소화
    - 인증을 부가 기능으로 판단, 추상화하여 header에 user_id를 입력받는 방식으로 간단히 구현하였습니다.
2. 커서 기반 페이징
    - 서비스 상 필요한 무한 스크롤 구현을 위해 커서 기반 페이징을 구현했습니다.  
3. N+1 문제 발생 쿼리 0건
    - 성능 상 큰 문제를 야기할 수 있는 JPA N+1 문제에 최대한 보수적으로 접근했습니다.
4. 동시성 문제
    - 구동 시 발생할 수 있는 인원 초과 문제를 최대한 간단히 `synchronized`를 통해 해결하였습니다.

#### 1. 페어 프로그래밍
Coding Convention을 맞추고, 각자의 개발 스타일을 파악하기 위해 `로그인 기능 구현` 이슈를 **페어 프로그래밍**으로 진행했습니다.

#### 2. Nested
Nested 라는 컨셉의 두가지 도전을 진행했습니다.
1. Nested DTO
    - DTO 클래스가 많아져 피로가 발생하는 상황을 종종 겪었습니다. 이를 개선하기 위해 static inner class로 DTO 클래스를 관리해봤습니다.
    - 도메인(상위 클래스) → 역할(하위 클래스)로 구분되어 **가독성** 및 **응집도** 증가를 느꼈습니다.
    - 다만, 애플리케이션 규모가 더 커진다면 이전과 동일한 피로가 발생할 수 있을 것 같습니다.
  
2. Nested Test
    - `@Nested`를 통한 BDD Style의 테스트를 도입했습니다.
    - 테스트 케이스 구분이 명확해졌으며, 비즈니스 흐름 파악이 보다 용이해진 느낌을 받았습니다.
