# OAuth 2.0 Client - oauth2Login()

## OAuth2LoginConfigurer 초기화 이해

### 설정 클래스 생성

```java
@Configuration(proxyBeanMethods = false)
public class CustomOAuth2ClientConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Client();
        return http.build();
    }
}

```

- API 설정
  - SecurityFilterChain 타입의 빈을 생성해서 보안 필터를 구성한다
  - HttpSecurity에 있는 oauth2Login()과 oauth2Client() API를 정의하고 빌드한다

<img width="1326" alt="image" src="https://user-images.githubusercontent.com/40031858/200452979-720a787c-f49a-49f0-aa1b-3412cfcbe2fa.png">

<img width="1333" alt="image" src="https://user-images.githubusercontent.com/40031858/200453015-6886e64e-6152-474c-90ba-e73c80331816.png">

<img width="1376" alt="image" src="https://user-images.githubusercontent.com/40031858/200453075-c21ffe79-f84c-4dfb-afc5-fe0666067ea7.png">

---

## OAuth2 로그인 구현 - OAuth 2.0 Login Page 생성

<img width="1403" alt="image" src="https://user-images.githubusercontent.com/40031858/200476475-40b9cf31-116c-4548-9bf9-f666a2ff8154.png">


---

## OAuth2 로그인 구현 - Authorization Code 요청하기

### 개요

- 주요 클래스
  - OAuth2AuthorizationRequestRedirectFilter
    - 클라이언트는 사용자의 브라우저를 통해 인가 서버의 권한 부여 엔드포인트로 리다이렉션하여 권한 코드 부여 흐름을 시작한다
    <img width="820" alt="image" src="https://user-images.githubusercontent.com/40031858/200557447-ea614f05-7fe8-4e65-887f-54f7554d2f30.png">
  - 요청 매핑 Url
    - AuthorizationRequestMatcher : /oauth2/authroziation/{registrationId}*
    - AuthorizationEndpointConfig. authorizationRequestBaseUri 를 통해 재정의될 수 있다
  - DefaultOAuth2AuthorizationRequestResolver
    - 웹 요청에 대하여 OAuth2AuthorizationRequest 객체를 최종 완성한다
    - /oauth2/authorization/{registrationId} 와 일치하는지 확인해서 일치하면 registrationId를 

        추출하고 이를 사용해서 ClientRegistration을 가져와 OAuth2AuthorizationRequest 를 빌드한다.

        <img width="503" alt="image" src="https://user-images.githubusercontent.com/40031858/200560891-7dd2ef64-32db-4dc6-9afa-4f36110981ff.png">

  - OAuth2AuthorizationRequest
    - 토큰 엔드포인트 요청 파라미터를 담은 객체로서 인가 응답을 연계하고 검증할 때 사용한다
  
        <img width="669" alt="image" src="https://user-images.githubusercontent.com/40031858/200561191-93771725-2b7a-447c-9fc7-b4888a44860a.png">

  - OAuth2AuthorizationRequestRepository
    - 인가 요청을 시작한 시점부터 인가 요청을 받는 시점까지 (리다이렉트) OAuth2AuthorizationRequest 를 유지해준다 


<img width="1444" alt="image" src="https://user-images.githubusercontent.com/40031858/200561560-3bcfb5ac-7f8c-4464-a1cc-d895e87267db.png">











