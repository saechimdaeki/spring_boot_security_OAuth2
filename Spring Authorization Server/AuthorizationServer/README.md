# Spring Authorization Server

## RegisteredClient / RegisteredClientRepository

### RegisteredClient

- 인가서버에 등록된 클라이언트를 의미하며 클라이언트가 authorization_code 또는 client_credentials와 같은 권한 부여 흐름을 시작하려면 먼저 클라이언트를 권한 부여 서버에 등록해야 한다
- 클라이언트 등록 시 클라이언트는 고유한 client_id,  client_secret 및 고유한 클라이언트 식별자와 연결된 메타데이터를 할당한다. 

- 클라이언트의 메타데이터는 클라이언트 이름부터 프로토콜 흐름과 관련된 항목(예: 유효한 리다이렉션 URI 목록)까지 다양하다
  - Spring Security의 OAuth2 Client 지원에서 해당 클라이언트 등록 모델은 ClientRegistration 이다
- 클라이언트의 주요 목적은 보호된 리소스에 대한 액세스를 요청하는 것으로 클라이언트는 먼저 권한 부여 서버를 인증하고 액세스 토큰과 교환을 요청합니다. 
- 권한 부여 서버는 클라이언트 및 권한 부여를 인증하고 유효한 경우 액세스 토큰을 발급하고 클라이언트는 액세스 토큰을 표시하여 리소스 서버에서 보호된 리소스를 요청할 수 있다.


<img width="1067" alt="image" src="https://user-images.githubusercontent.com/40031858/210751198-98a728af-d081-4eb0-82f1-7060bf49b587.png">


<img width="1090" alt="image" src="https://user-images.githubusercontent.com/40031858/210751263-332bddb7-c1f3-40c2-b367-b2950d96714c.png">

### RegisteredClientRepository

- 새로운 클라이언트를 등록하고 기존 클라이언트를 조회할 수 있는 저장소 클래스
- 클라이언트 인증, 권한 부여 처리, 토큰 자체 검사, 동적 클라이언트 등록 등과 같은 특정 프로토콜 흐름 시 다른 구성 요소에서 참조한다.
- 제공하는 구현체로 InMemoryRegisteredClientRepository 및 JdbcRegisteredClientRepository 가 있다

<img width="911" alt="image" src="https://user-images.githubusercontent.com/40031858/210751402-3e032029-d2c8-40a3-bfb7-046cb9c66105.png">


## OAuth2AuthorizationService / OAuth2Authorization 이해 및 활용

### OAuth2Authorization

- 리소스 소유자의 역할이 있는 권한 부여 방식인 경우 클라이언트에 부여된 권한 부여 즉 인가 상태를 유지하는 클래스
- Spring Security의 OAuth2 Client 의 해당 인증 모델은 OAuth2AuthorizedClient 와 서로 대응하는 개념이다.
- 권한 부여 흐름이 성공적으로 완료되면 OAuth2Authorization이 생성되고 AccessToken 이 저장되며 선택적으로 RefreshToken, IDToken 등이 저장된다.
- 생성된 OAuth2Authorization 은 OAuth2AuthorizationService 에 의해 메모리나 DB 에 저장된다
- OAuth2Authorization 에 저장되는 OAuth2Token 타입들은 권한 부여 유형 및 Scope 에 따라 다르다

### OAuth2AuthorizationService

- OAuth2AuthorizationService 는 새로운 OAuth2Authorization 을 저장하고 기존 OAuth2Authorization 을 검색하는 구성요소이다
- 특정 엔드포인트 프로토콜 흐름을 따를 때 다른 구성 요소에서 사용된다(예: 클라이언트 인증, 권한 부여 처리, 토큰 자체 검사, 토큰 취소, 동적 클라이언트 등록 등).
- 제공되는 기본 구현체는 InMemoryOAuth2AuthorizationService 및 JdbcOAuth2AuthorizationService 이 있다. 
- InMemoryOAuth2AuthorizationService 개발 및 테스트에만 사용하는 것이 좋으며 기본값이다
- JdbcOAuth2AuthorizationService 는 JdbcTemplate 를 사용하여 OAuth2Authorization 객체를 DB 에 저장하여 상태를 계속 유지하도록 한다


<img width="1104" alt="image" src="https://user-images.githubusercontent.com/40031858/210756147-c4bc26cb-3c5a-40ea-894a-e2235dcb3e06.png">


----


----


# Spring Authorization Server - 엔드포인트 프로토콜


## OAuth 2.0 AuthorizationServer Endpoint 기능 및 특징

### OAuth2AuthorizationEndpointConfigurer 

- OAuth2 권한 부여 엔드포인트에 대한 사용자 정의 할 수 있는 기능을 제공한다. 
- OAuth2 권한 부여 요청에 대한 전처리, 기본 처리 및 후처리 로직을 커스텀하게 구현할 수 있도록 API를 지원한다
- OAuth2AuthorizationEndpointFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다

### OAuth2AuthorizationEndpointFilter

- OAuth2 인증 요청(및 동의)을 처리하는 필터이며 다음과 같은 기본값으로 구성된다 
- OAuth2AuthorizationCodeRequestAuthenticationConverter – 클라이언트 요청 파라미터를 OAuth2AuthorizationCodeRequestAuthenticationToken 으로 변환하고 AuthenticationProvider 에게 전달한다
- OAuth2AuthorizationCodeRequestAuthenticationProvider 
  - Authorization Code 권한 부여 방식을 처리하는 OAuth 2.0 인증 요청 및 동의에 대한 AuthenticationProvider 구현체이다


### RequestMatcher

- Code 요청 패턴
  - /oauth2/authorize, GET, /oauth2/authorize, POST

- Consent (동의하기) 요청 패턴
  - /oauth2/authorize, POST



## OAuth 2.0 AuthorizationServer Endpoint

<img width="1128" alt="image" src="https://user-images.githubusercontent.com/40031858/210764782-5b7376e5-1b09-4577-bed1-767101bb92c1.png">

<img width="1153" alt="image" src="https://user-images.githubusercontent.com/40031858/210764852-4b9df10f-16e7-4665-9d74-d45eabd7cd33.png">


### OAuth2AuthorizationConsent

- OAuth2AuthorizationConsent 는 OAuth2 권한 부여 요청 흐름의 권한부여  "동의“ (결정)를 나타낸다. 
- 클라이언트에 대한 액세스를 승인할 때 리소스 소유자는 클라이언트가 요청한 권한의 하위 집합만 허용할 수 있습니다. 
- 클라이언트가 범위를 요청하고 리소스 소유자가 요청된 범위에 대한 액세스를 허용하거나 거부하는 authorization_code grant 흐름이다
- OAuth2 인증 요청 흐름이 완료되면 OAuth2 Authorization Consent가 생성(또는 업데이트)되고 부여된 권한을 클라이언트 및 리소스 소유자와 연결한다

<img width="927" alt="image" src="https://user-images.githubusercontent.com/40031858/210916879-b9145d73-3125-43bb-abcf-df94bfe9e4e9.png">


### OAuth2AuthorizationConsentService

- OAuth2AuthorizationConsent 저장되고 기존 OAuth2AuthorizationConsent 를 조회하는 클래스로 주로 OAuth2 권한 부여 요청 흐름을 구현하는 구성 요소에 의해 사용된다
- 기본 구현체는 InMemoryOAuth2AuthorizationConsentService 와 JdbcOAuth2AuthorizationConsentService 가 있다

<img width="433" alt="image" src="https://user-images.githubusercontent.com/40031858/210916969-d42914a2-bcb7-4cc0-939e-c3a7995e38c8.png">

<img width="1143" alt="image" src="https://user-images.githubusercontent.com/40031858/210917045-d725371b-c6cc-4172-8c6a-7bbd931877af.png">

<img width="1140" alt="image" src="https://user-images.githubusercontent.com/40031858/210917072-7fe3ca52-39e3-4a21-9de3-607416dd2e21.png">

---


## OAuth 2.0 Token Endpoint

### OAuth2TokenEndpointConfigurer
- OAuth2 토큰 엔드포인트에 대한 사용자 정의 할 수 있는 기능을 제공한다. 
- OAuth2 토큰 요청에 대한 전처리, 기본 처리 및 후처리 로직을 커스텀하게 구현할 수 있도록 API를 지원한다
- OAuth2TokenEndpointFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다
- 지원되는 권한 부여 유형은 authorization_code, refresh_token 및 client_credential 이다

### OAuth2TokenEndpointFilter
- 클라이언트의 토큰 요청을 처리하는 필터이며 다음과 같은 기본값으로 구성된다 
- DelegatingAuthenticationConverter – 각 특정 유형의 AuthenticationConverter 를 호출해서 처리를 위임한다
  - OAuth2AuthorizationCodeAuthenticationConverter – HttpServletRequest 정보를 OAuth2AuthorizationCodeAuthenticationToken 로 변환하여 반환
  - OAuth2RefreshTokenAuthenticationConverter - HttpServletRequest 정보를 OAuth2RefreshTokenAuthenticationToken 로 변환하여 반환
- OAuth2ClientCredentialsAuthenticationConverter - HttpServletRequest 정보를 OAuth2ClientCredentialsAuthenticationToken 로 변환하여 반환
- OAuth2AuthorizationCodeAuthenticationProvider, OAuth2RefreshTokenAuthenticationProvider, OAuth2ClientCredentialsAuthenticationProvider 
  - 권한 부여 유형에 따라 토큰을 발행하는 AuthenticationProvider 구현체이다
- AuthenticationSuccessHandler - 인증된 OAuth2AccessTokenAuthenticationToken 을 처리하는 내부 구현체로서 인증토큰을 사용하여 OAuth2AccessTokenResponse 를 반환한다.
- AuthenticationFailureHandler - OAuth2AuthenticationException 과 관련된 OAuth2Error를 사용하는 내부 구현 인증예외이며 OAuth2Error 응답을 반환한다

### RequestMatcher
- 토큰 요청 패턴
  - /oauth2/token, POST

```java
@Bean
public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {
  Oauth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

  http.apply(authorizationServerConfigurer);

  authorizationServerConfigurer
    .tokenEndpoint(tokenEndpoint -> 
        tokenEndpoint
          .accessTokenRequestConverter(accessTokenRequestConverter)
          .authenticationProvider(authenticationProvider)
          .accessTokenResponseHandler(accessTokenResponseHandler)
          .errorResponseHandler(errorResponseHandler)    
    );
  return http.build();
}
```


---

# OAuth 2.0 Token Endpoint

## 클라이언트 인증하기

### OAuth2ClientAuthenticationConfigurer 

- OAuth2 클라이언트 인증을 위한 사용자 정의하는 기능을 제공한다. 
- 클라이언트 인증 요청에 대한 전처리, 기본 처리 및 후처리 로직을 커스텀하게 구현할 수 있도록 API를 지원한다
- OAuth2ClientAuthenticationFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다
- 지원되는 클라이언트 인증 방법은 client_secret_basic, client_secret_post, private_key_jwt, client_secret_jwt및 none(공개 클라이언트) 이다

### OAuth2ClientAuthenticationFilter
- 클라이언트 인증 요청을 처리하는 필터이며 다음과 같은 기본값으로 구성된다 
- DelegatingAuthenticationConverter
  - ClientSecretBasicAuthenticationConverter – 클라이언트 요청 방식이 HTTP Basic 일 경우 처리
  - ClientSecretPostAuthenticationConverter – 클라이언트 요청 방식이 POST 일 경우 처리
  - JwtClientAssertionAuthenticationConverter - 클라이언트 요청 방식이 JWT 토큰일 경우 처리
  - PublicClientAuthenticationConverter - 클라이언트 요청 방식이 PKCE 일 경우 처리
- DelegatingAuthenticationProvider
  - ClientSecretAuthenticationProvider, JwtClientAssertionAuthenticationProvider, PublicClientAuthenticationProvider 
  - 권한 부여 유형에 따라 토큰을 발행하는 AuthenticationProvider 구현체이다
- AuthenticationSuccessHandler - 인증된 OAuth2ClientAuthenticationToken  에 SecurityContext 를 연결하는 내부 구현체
- AuthenticationFailureHandler – 연결된 OAuth2AuthenticationException 를 사용하여 OAuth2 오류 응답을 반환하는 내부 구현체

### RequestMatcher
- 토큰 요청 패턴
  - POST /oauth2/token, POST /oauth2/introspect, POST /oauth2/revoke


<img width="1126" alt="image" src="https://user-images.githubusercontent.com/40031858/210937712-b57e2efd-1a5b-4266-a79c-997e23381b98.png">


```java
@Bean
public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {
  Oauth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

  authorizationServerConfigurer
    .clientAuthentication(clientAuthentication -> 
      clientAuthentication.autrhenticationConverter(authenticationConverter)
      .authenticationProvider(authenticationProvider)
      .authenticationSuccessHandler(authenticationSuccessHandler)
      .errorResponseHandler(errorResponseHandler)
    );

  return http.build();
}
```

## Authorization Code

<img width="1152" alt="image" src="https://user-images.githubusercontent.com/40031858/210939301-66723056-2361-40ea-801b-dda9061ec5cd.png">


### Access Token Response

- Successful Response
  - access_token(필수) - 권한 부여 서버에서 발급한 액세스 토큰 문자열
  - token_type(필수) - 토큰 유형은 일반적으로 "Bearer" 문자열
  - expires_in(권장) – 토큰의 만료시간
  - refresh_token(선택 사항) - 액세스 토큰이 만료되면 응용 프로그램이 다른 액세스 토큰을 얻는 데 사용할 수 있는   Refresh 토큰을 반환하는 것이 유용하다. 
       
       단, implicit 권한 부여로 발행된 토큰은 새로고침 토큰을 발행할 수 없다.
  - scope(선택사항) - 사용자가 부여한 범위가 앱이 요청한 범위와 동일한 경우 이 매개변수는 선택사항. 

- Unsuccessful Response
  - invalid_request - 요청에 매개변수가 누락, 지원되지 않는 매개변수, 매개변수 반복되는 경우 서버가 요청을 진행할 수 없음
  - invalid_client - 요청에 잘못된 클라이언트 ID 또는 암호가 포함된 경우 클라이언트 인증에 실패, HTTP 401 응답
  - invalid_grant - 인증 코드가 유효하지 않거나 만료됨

    권한 부여에 제공된 리디렉션 URL이 액세스 토큰 요청에 제공된 URL과 일치하지 않는 경우 반환하는 오류
  - invalid_scope - 범위를 포함하는 액세스 토큰 요청의 경우 이 오류는 요청의 유효하지 않은 범위 값을 나타냄
  - unauthorized_client - 이 클라이언트는 요청된 권한 부여 유형을 사용할 권한이 없음(RegisteredClient 에 정의하지 않은 권한 부여 유형을 요청한 경우)
  - unsupported_grant_type - 권한 부여 서버가 인식하지 못하는 승인 유형을 요청하는 경우 이 코드를 사용함

 ## Client Credentials

 <img width="1145" alt="image" src="https://user-images.githubusercontent.com/40031858/211004969-daae40df-c85f-49f0-ad15-72443d8acceb.png">

## Refresh Token

<img width="1125" alt="image" src="https://user-images.githubusercontent.com/40031858/211008496-3e825c1e-2849-422a-8133-dc5bdd175112.png">

## Authorization Code with PKCE

<img width="1121" alt="image" src="https://user-images.githubusercontent.com/40031858/211008880-12bcfd42-3366-4cf9-a19c-b362a27fb2d7.png">

<img width="1156" alt="image" src="https://user-images.githubusercontent.com/40031858/211008916-f9178755-4d6d-42ed-bc16-2fa81f2a5d39.png">

<img width="1136" alt="image" src="https://user-images.githubusercontent.com/40031858/211008988-72d6e8f3-7693-4219-b781-c58794b422ab.png">


## OAuth 2.0 Token Introspection Endpoint


### OAuth2TokenIntrospectionEndpointConfigurer
- OAuth2 토큰 검사 엔드포인트에 대한 사용자 정의 할 수 있는 기능을 제공한다. 
- OAuth2 검사 요청에 대한 전처리, 기본 처리 및 후처리 로직을 커스텀하게 구현할 수 있도록 API를 지원한다
- OAuth2TokenIntrospectionEndpointFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다

### OAuth2TokenIntrospectionEndpointFilter 
- OAuth2 검사 요청을 처리하는 필터이며 다음과 같은 기본값으로 구성된다 
- IntrospectionRequestConverter
  - OAuth2 검사 요청을 추출하려고 할 때 사용되는 전처리기로서 OAuth2TokenIntrospectionAuthenticationToken 을 반환한다
- OAuth2TokenIntrospectionAuthenticationProvider 
  - OAuth2TokenIntrospectionAuthenticationToken 를 받아 인증 처리를 하는 AuthenticationProvider 구현체이다


<img width="957" alt="image" src="https://user-images.githubusercontent.com/40031858/211009571-b6dab851-1353-41fb-bee9-936cb851af83.png">

## OAuth 2.0 Token Revocation Endpoint 

### OAuth2TokenRevocationEndpointConfigurer  

- OAuth2 토큰 취소 엔드포인트에 대한 사용자 정의 할 수 있는 기능을 제공한다. 
- OAuth2 토큰 취소에 대한 전처리, 기본 처리 및 후처리 로직을 커스텀하게 구현할 수 있도록 API를 지원한다
- OAuth2TokenRevocationEndpointFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다

### OAuth2TokenRevocationEndpointFilter 

- OAuth2 토큰 취소를 처리하는 필터이며 다음과 같은 기본값으로 구성된다 
- DefaultTokenRevocationAuthenticationConverter
  - OAuth2 토큰 취소를 추출하려고 할 때 사용되는 전처리기로서 OAuth2TokenRevocationAuthenticationToken 을 반환한다
- OAuth2TokenRevocationAuthenticationProvider 
  - OAuth2TokenRevocationAuthenticationToken 을 전달받아 인증처리를 하는 AuthenticationProvider 구현체이다.

### RequestMatcher
- 토큰 취소 요청 패턴
  - /oauth2/revoke, POST

<img width="963" alt="image" src="https://user-images.githubusercontent.com/40031858/211021278-ac0e360c-202e-452f-89e3-b83458d2b23f.png">


## OAuth 2.0 Authorization Server Metadata Endpoint / JWK Set Endpoint 

<img width="882" alt="image" src="https://user-images.githubusercontent.com/40031858/211021367-98018424-7639-4626-a107-2615b2cb8d45.png">

<img width="984" alt="image" src="https://user-images.githubusercontent.com/40031858/211021409-f565815d-ca68-43f3-a292-d9812ac8c448.png">

<img width="675" alt="image" src="https://user-images.githubusercontent.com/40031858/211021442-ca1519c6-1128-4c37-a7dd-e83cec50ea7b.png">


## OpenID Connect 1.0  Endpoint

### OpenID Connect 1.0 Provider Configuration Endpoint  

- OidcConfigurer 는 OpenID Connect 1.0 Provider Configuration 엔드포인트 대한 지원을 제공한다. 
- OidcConfigurer 는 OidcProviderConfigurationEndpointFilter 를 구성하고 이를 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다
- OidcProviderConfigurationEndpointFilter 는 OidcProviderConfiguration 응답을 처리한다

### OpenID Connect 1.0 UserInfo Endpoint

- OidcUserInfoEndpointConfigurer 는 OpenID Connect 1.0 UserInfo 엔드포인트 사용자 정의하는 기능을 제공한다
- OidcUserInfoEndpointFilter를 구성하고 OAuth2 인증 서버 SecurityFilterChain 빈에 등록한다

### OidcUserInfoEndpointFilter 

- UserInfo 요청을 처리하고 OidcUserInfo 응답을 반환하는 필터이며 다음과 같은 기본값으로 구성된다 
- OidcUserInfoAuthenticationProvider 
  - 요청된 scope 를 기준으로 ID 토큰에서 표준 클레임을 추출하는 userInfoMapper 을 가지고 있다
### RequestMatcher
- 토큰 검사 요청 패턴
  - /userinfo, POST 
  - /userinfo, GET




