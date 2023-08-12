# Spring and Firebase Authentication

## 프로젝트 목적

- `Firebase Authentication`, `Spring Security`을 활용한 백엔드 서버 구현
    - `Spring Security`의 필터, 예외 처리 학습
    - 에러, 예외 처리 세분화 연습

## 정리

### 1. ExceptionTranslationFilter

|                      | **AuthenticationEntryPoint** | **AccessDeniedHandler** |
|:--------------------:|:----------------------------:|:-----------------------:|
|      **Target**      |      인증(Authentication)      |    인가(Authorization)    |
| **HTTP Status Code** |             401              |           403           |
|   **Description**    |         인증에 실패하는 경우          | 인증은 성공했으나, 접근 권한이 없는 경우 |

- 프로젝트에서 `AuthenticationEntryPoint`를 적용해 봤으나 프로젝트의 규모가 작다고 판단해 `FirebaseTokenFilter`에서 예외 처리 결정

|        | **AuthenticationEntryPoint 예외 처리** |  **CustomFilter 예외 처리**  |
|:------:|:----------------------------------:|:------------------------:|
| **장점** |     전역적인 예외 처리, 보안 관련 로직 분리 가능     |        유연한 예외 처리         |
| **단점** |           인증과 관련된 예외만 처리           | 코드 중복 가능성, 구현 복잡도 증가 가능성 |

### 2. HttpSecurity의 permitAll()

- `permitAll()`은 필터를 무시한다는 것이 아닌 항상 접근 권한이 부여된다는 뜻이다.
- 필터를 무시하고 접근하고 싶다면 `WebSecurityConfigurerAdapter`의 `WebSecurity.ignoring()`을 사용해야 한다.

### 3. OncePerRequestFilter

- 한 요청에 대해 한 번만 실행되는 필터
- `alreadyFilteredAttributeName`과 `hasAlreadyFilteredAttribute`를 활용한 로직을 통해 필터 실행 여부 확인
- 불필요한 필터링 방지

## Reference

- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Spring Security with filters permitAll not working](https://stackoverflow.com/questions/46068433/spring-security-with-filters-permitall-not-working)
- [OncePerRequestFilter와 Filter의 차이](https://minkukjo.github.io/framework/2020/12/18/Spring-142/)
- [What is OncePerRequestFilter?](https://stackoverflow.com/questions/13152946/what-is-onceperrequestfilter)
