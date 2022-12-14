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

---

## OAuth2 로그인 구현 - Access Token 교환하기

### 개요

- 주요 클래스
  - OAuth2LoginAuthenticationFilter
    - 인가서버로부터 리다이렉트 되면서 전달된 code를 인가서버의 Access Token으로 교환하고 Access Token이 저장된

      OAuth2LoginAuthenticationToken을 AuthenticationManager에 위임하여 UserInfo 정보를 요청해서 최종 사용자로 로그인한다

    - OAuth2AuthorizedClientRepository를 사용하여 OAuth2AuthorizedClient를 저장한다
    - 인증에 성공하면 OAuth2AuthenticationTOken이 생성되고 SecurityContext에 저장되어 인증 처리를 완료한다


    <img width="642" alt="image" src="https://user-images.githubusercontent.com/40031858/200713159-a3655deb-4ab7-4ad5-9570-cd27345274a8.png">

    - 요청 매핑 Url
      - RequestMatcher : /login/oauth2/code/* 

  - OAuth2LoginAuthenticationProvider
    - 인가서버로부터 리다이렉트 된 이후 프로세스를 처리하며  Access Token 으로 교환하고 이 토큰을 사용하여 UserInfo 처리를 담당한다
    - Scope 에 openid 가 포함되어 있으면 OidcAuthorizationCodeAuthenticationProvider 를 호출하고 아니면 OAuth2AuthorizationCodeAuthenticationProvider 를 호출하도록 제어한다 

       <img width="538" alt="image" src="https://user-images.githubusercontent.com/40031858/200713324-e605406a-40cc-412a-8270-cf8659188c88.png">


  - OAuth2AuthorizationCodeAuthenticationProvider
    - 권한 코드 부여 흐름을 AuthenticationProvider
    - 인가서버에 Authorization Code 처리하는와 AccessToken 의 교환을 담당하는 클래스

      <img width="526" alt="image" src="https://user-images.githubusercontent.com/40031858/200713458-a58465ee-0d84-4007-9807-714d96067998.png">

  
  - OidcAuthorizationCodeAuthenticationProvider
    - OpenID Connect Core 1.0 권한 코드 부여 흐름을 처리하는 AuthenticationProvider 이며 요청 Scope 에 openid 가 존재할 경우 실행된다

      <img width="503" alt="image" src="https://user-images.githubusercontent.com/40031858/200714192-b4ddf682-7ea1-4066-84ee-3fe77471e02a.png">

  - DefaultAuthorizationCodeTokenResponseClient
    - 인가서버의 token 엔드 포인트로 통신을 담당하며 AccessToken 을 받은 후 OAuth2AccessTokenResponse 에 저장하고 반환한다


      <img width="504" alt="image" src="https://user-images.githubusercontent.com/40031858/200714354-cd77023c-a001-46d8-a114-6edc128138d7.png">


<img width="1124" alt="image" src="https://user-images.githubusercontent.com/40031858/200714392-49ed1517-40cb-4a85-ae85-848e07964461.png">




<img width="1153" alt="image" src="https://user-images.githubusercontent.com/40031858/200714524-3a4975bf-fed7-42cb-b305-2bb7592811e4.png">

<img width="1157" alt="image" src="https://user-images.githubusercontent.com/40031858/200714596-309cae53-3afe-434e-864b-7466259127a6.png">


---

# OAuth 2.0 User 모델 소개

## OAuth2UserService

### 개요

- OAuth2UserService
  - 액세스 토큰을 사용해서 UserInfo 엔드포인트 요청으로 최종 사용자의 (리소스 소유자) 속성을 가져오며 OAuth2User 타입의 객체를 리턴한다
  - 구현체로 DefaultOAuth2UserService 와 OidcUserService 가 제공된다


  - DefaultOAuth2UserService
    - 표준 OAuth 2.0 Provider를 지원하는 OAuth2UserService 구현체다
    - OAuth2UserRequest 에 Access Token 을 담아 인가서버와 통신 후 사용자의 속성을 가지고 온다
    - 최종 OAuth2User 타입의 객체를 반환한다

  - OidcUserService
    - OpenID Connect 1.0 Provider를 지원하는 OAuth2UserService 구현체다
    - OidcUserRequest 에 있는 ID Token 을 통해 인증 처리를 하며 필요시 DefaultOAuth2UserService 를 사용해서 UserInfo 엔드포인트의 사용자 속성을 요청한다
    - 최종 OidcUser 타입의 객체를 반환한다

<img width="1125" alt="image" src="https://user-images.githubusercontent.com/40031858/201246022-26edd14e-d3a2-42c8-83cf-527f9f1d7dee.png">

<img width="1122" alt="image" src="https://user-images.githubusercontent.com/40031858/201246054-2c86efbf-8f65-4e97-bfb5-8b51ff3b4398.png">


## OAuth2User & OidcUser

### 개요

- 시큐리티는 UserAttributes 및 ID Token Claims 을 집계 & 구성하여 OAuth2User 와 OidcUser 타입의 클래스를 제공한다

- OAuth2User
  - OAuth 2.0 Provider 에 연결된 사용자 주체를 나타낸다
  - 최종 사용자의 인증에 대한 정보인 Attributes 를 포함하고 있으며 first name, middle name, last name, email, phone number, address 등으로 구성된다
  - 기본 구현체는 DefaultOAuth2User 이며 인증 이후 Authentication 의 principal 속성에 저장된다
- OidcUser
  - OAuth2User 를 상속한 인터페이스이며 OIDC Provider 에 연결된 사용자 주체를 나타낸다
  - 최종 사용자의 인증에 대한 정보인 Claims 를 포함하고 있으며 OidcIdToken 및 OidcUserInfo 에서 집계 및 구성된다
  - 기본 구현체는 DefaultOidcUser 이며 DefaultOAuth2User 를 상속하고 있으며 인증 이후 Authentication 의 principal 속성에 
     저장된다 


<img width="1073" alt="image" src="https://user-images.githubusercontent.com/40031858/201247511-7153a851-6ce3-49f1-811b-9e8247dafa8b.png">

<img width="1149" alt="image" src="https://user-images.githubusercontent.com/40031858/201247538-bfac5bdb-8e5f-4c99-ad57-c2ca925c3e49.png">






---

## OAuth 2.0 Provider UserInfo 엔드포인트 요청하기

### 개요

- 주요 클래스 
  - DefaultOAuth2UserService
    - public OAuth2User loadUser(OAuth2UserRequest userRequest)
  - OAuth2UserRequestEntityConverter
    - OAuth2UserRequest 를 RequestEntity 로 컨버터 한다

    <img width="499" alt="image" src="https://user-images.githubusercontent.com/40031858/201553927-fe187552-d6bf-4ecd-bac9-4aabb69e2c1b.png">

  - RestOperations
    - RequestEntity 로 인가서버에 요청하고 ResponseEntity 로 응답받는다
    - OAuth2User 타입의 객체를 반환한다

- 요청 Uri
  - POST /userinfo

<img width="1439" alt="image" src="https://user-images.githubusercontent.com/40031858/201553984-f58cb670-7346-484b-b695-21aa7b21dcfa.png">

## OpenID Connect Provider UserInfo 엔드포인트 요청하기

### 개요

- 주요 클래스
  - OidcUserService
    - public OidcUser loadUser(OidcUserRequest userRequest)

      <img width="359" alt="image" src="https://user-images.githubusercontent.com/40031858/201554464-3ba88fb9-77f3-48a9-b9c4-5fdadb24d9f6.png">

    - 내부에 DefaultOAuth2UserService 를 가지고 있으며 OIDC 사양에 부합할 경우 OidcUserRequest 를 넘겨 주어 인가서버와 통신한다
    - OidcUser 타입의 객체를 반환한다 

- 요청 Uri
  - POST /userinfo


<img width="1425" alt="image" src="https://user-images.githubusercontent.com/40031858/201554526-aa3abbe8-1048-43a3-b6df-25e4da05a079.png">

<img width="1424" alt="image" src="https://user-images.githubusercontent.com/40031858/201554543-76b4cef8-2d8a-4635-b4ad-9f8835013b1b.png">


---

## OpenID Connect 로그아웃


- 개념
  - 클라이언트는 로그아웃 엔드포인트를 사용하여 웹 브라우저에 대한 세션과 쿠키를 지운다.
  - 클라이언트 로그아웃 성공 후 OidcClientInitiatedLogoutSuccessHandler 를 호출하여 OpenID Provider 세션 로그아웃 요청한다
  - OpenID Provider 로그아웃이 성공하면 지정된 위치로 리다이렉트 한다
  - 인가서버 메타데이터 사양에 있는 로그아웃 엔드 포인트는 end_session_endpoint 로 정의되어 있다
    - endSessionEndpoint = http://localhost:8080/realms/oauth2/protocol/openid-connect/logout

- API 설정

```java
http
  .logout()
  .logoutSuccessHandler(oidcLogoutSuccessHandler())
  .invalidateHttpSession(true)
  .clearAuthentication(true)
  .deleteCookies("JSESSIONID");

private OidcClientInitialtedLogoutSuccessHandler oidcLogoutSuccessHandler(){
  OidcClientInitialtedLogoutSuccessHandler successHandler = 
    new OidcClientInitialtedLogoutSuccessHandler(clientRegistrationRepository);

  successHandler.setPostLogoutRedirectUri("http://localhost:8081/login");
  return successHandler;
}
```

<img width="523" alt="image" src="https://user-images.githubusercontent.com/40031858/201556830-49634d96-8d4e-4c2b-8268-55be6ea2e692.png">

---

## Spring MVC 에서 인증 객체 참조하기

- Authentication
  - public void dashboard(Authentication authentication) {} 
    - oauth2Login() 로 인증을 받게 되면 Authentication 은 OAuth2AuthenticationToken 타입의 객체로 바인딩 된다
    - principal 에는 OAuth2User 타입 혹은 OidcUser 타입의 구현체가 저장 된다.
    - DefaultOAuth2User 는 /userInfo 엔드포인트 요청으로 받은 User 클레임 정보로 생성된 객체이다
    - DefaultOidcUser 는 OpenID Connect 인증을 통해 ID Token 및 클레임 정보가 포함된 객체이다

- @AuthenticationPrincipal
  - public void dashboard(@AuthenticationPrincipal OAuth2User principal or OidcUser principal) {}
  - AuthenticationPrincipalArgumentResolver 클래스에서 요청을 가로채어 바인딩 처리를 한다. 
    - Authentication 를 SecurityContex 로부터 꺼내어 와서 Principal 속성에 OAuth2User 혹은 OidcUser 타입의 객체를 저장한다


<img width="412" alt="image" src="https://user-images.githubusercontent.com/40031858/201647922-805e66da-7ba6-488c-8d07-bd3190fe5a1d.png">


---

## Authorization BaseUri & Redirection BaseUri

```java
http
  .oauth2Login(oauth2 -> oauth
    .loginPage("/login")
    .loginProcessingUrl("/login/v1/oauth2/code/*")
    .authorizationEndpoint(authorizationEndpointConfig -> 
        authorizationEndpointConfig.baseUri("/oauth2/v1/authorization"))
    .redirectionEndpoint(redirectionEndpointConfig -> 
        redirectionEndpointConfig.baseUri("/login/v1/oauth2/code/*"))
  );
```

- authorizationEndpoint().baseUrl("/oauth2/v1/authorization")은 권한 부여 요청 BaseUri를 커스텀한다
  - 1단계 권한 부여 요청을 처리하는 OAuth2AuthorizationRequestRedirectFilter 에서 요청에 대한 매칭여부를 판단한다
  - 설정에서 변경한 값이 클라이언트의 링크 정보와 일치하도록 맞추어야 한다

<img width="1254" alt="image" src="https://user-images.githubusercontent.com/40031858/201911160-7f138fc1-dee8-4510-a449-d54eb2672095.png">



- redirectionEndpoint.baseUri("/login/v1/oauth2/code/*") 은 인가 응답의 baseUri 를 커스텀 한다
  - Token 요청을 처리하는 OAuth2LoginAuthenticationFilter 에서 요청에 대한 매칭여부를 판단한다
    - applicztion.yml 설정 파일에서 registration 속성의 redirectUri 설정에도 변경된 값을 적용해야 한다
    - 인가서버의 redirectUri 설정에도 변경된 값을 적용해야 한다
  - loginProcessingUrl("/login/v1/oauth2/code/*") 를 설정해도 결과는 동일하지만 redirectionEndpoint.baseUri 가 더 우선이다

<img width="1340" alt="image" src="https://user-images.githubusercontent.com/40031858/201911350-0eebfc88-5f39-4710-b902-ea8908502c2a.png">


---

## OAuth2AuthorizationRequestResolver

### OAuth2AuthorizationRequestResolver

- Authorization Code Grant 방식에서 클라이언트가 인가서버로 권한부여 요청할 때 실행되는 클래스
- OAuth2AuthorizationRequestResolver 는 OAuth 2.0 인가 프레임워크에 정의된 표준 파라미터 외에 다른 파라미터를 추가하는 식으로 인가 요청을 할 때 사용한다
- DefaultOAuth2AuthorizationRequestResolver 가 디폴트 구현체로 제공 되며 Consumer<OAuth2AuthorizationRequest.Builder> 속성에 커스텀 할 내용을 구현한다

```java
@Bean
SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
  http.authorizeRequests((requests) -> requests.antMatchers("/home").permitAll()
        .anyRequest().authenticated();
  http.oauth2Login(authLogin ->
      authLogin.authorizationEndpoint(authEndpoint -> 
        authEndpoint.authorizationRequestResolver(customOAuth2AuthenticationRequestResolver()))));
  return http.build();
}

private OAuth2AuthorizationRequestResolver customOAuth2AuthenticationRequestResolver() {
  return new CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository,"/oauth2/authorization");
}

```

<img width="900" alt="image" src="https://user-images.githubusercontent.com/40031858/201924604-318b88f3-b7e6-4894-8b6e-0fce631ff941.png">

<img width="1390" alt="image" src="https://user-images.githubusercontent.com/40031858/201924655-3725373b-d2fc-4033-b718-bddc286707ca.png">








































