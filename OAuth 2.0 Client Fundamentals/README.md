# OAuth 2.0 Client Fundamentals

## 클라이언트 앱 시작하기 - application.yml/ OAuth2ClientProperties

### 클라이언트 권한 부여 요청 시작 

1. 클라이언트가 인가서버로 권한 부여 요청을 하거나 토큰 요청을 할 경우 클라이언트 정보 및 엔드포인트 정보를 참조해서 전달한다
2. application.yml 환경설정 파일에 클라이언트 설정과 인가서버 엔드포인트 설정을 한다
3. 초기화가 진행되면 application.yml에 있는 클라이언트 및 엔드포인트 정보가 OAuth2ClientProperties의 각 속성에 바인딩 된다
4. OAuth2ClientProperties에 바인딩 되어 있는 속성의 값은 인가서버로 권한부여 요청을 하기 위한 ClientRegistration 클래스의 필드에 저장된다
5. OAuth2Client는 ClientRegistration를 참조해서 권한부여 요청을 위한 매개변수를 구성하고 인가서버와 통신한다

<img width="1314" alt="image" src="https://user-images.githubusercontent.com/40031858/200154054-a2890bf6-fb9b-4e5d-9d8b-4e2de2734850.png">

```yaml
server:
  port: 8081

spring:
  security:
    oauth2:
      client:
        registration: # 클라이언트 설정
          keycloak:
            clientId: oauth2-client-app  # 서비스 공급자에 등록된 클라이언트 아이디
            clientSecret: IUcUxC5no8zlGo7aWGLzdBguo92BWCV3  # 서비스 공급자에 등록된 클라이언트 비밀번호
            clientName: oauth2-client-app  # 클라이언트 이름
            redirectUri: http://localhost:8081/login/oauth2/code/keycloak # 인가서버에서 권한 코드 부여 후 클라이언트로 리다이렉트 하는 위치
            authorizationGrantType: authorization_code # OAuth 2.0 권한 부여 타입
            clientAuthenticationMethod: client_secret_post # 클라이언트 자격증명 전송 방식
            scope: openid,profile,email # 리소스에 접근 제한 범위
        provider: # 공급자 설정
          keycloak:
            authorizationUri: http://localhost:8080/realms/oauth2/protocol/openid-connect/auth # OAuth 2.0 권한 코드 부여 엔드 포인트
            tokenUri: http://localhost:8080/realms/oauth2/protocol/openid-connect/token # OAuth 2.0 토큰 엔드 포인트
            issuerUri: http://localhost:8080/realms/oauth2 # 서비스 공급자 위치
            userInfoUri: http://localhost:8080/realms/oauth2/protocol/openid-connect/userinfo # OAuth 2.0 UserInfo 엔드 포인트
            jwkSetUri: http://localhost:8080/realms/oauth2/protocol/openid-connect/certs # OAuth 2.0 JwkSetUri 엔드 포인트
            userNameAttribute: preferred_username # OAuth 2.0 사용자명을 추출하는 클레임명


```

<img width="1384" alt="image" src="https://user-images.githubusercontent.com/40031858/200154512-5cce6451-3265-4662-9983-f79e24f0f463.png">


---

## ClientRegistration 이해 및 활용

### 개념

- OAuth 2.0 또는 OpenID Connect 1.0 Provider 에서 클라이언트의 등록 정보를 나타낸다
- ClientRegistration은 OpenID Connect Provider의 설정 엔드포인트나 인가 서버의 메타데이터 엔드포인트를 찾아 초기화할 수 있다
- ClientRegistrations의 메소드를 사용하면 아래 예제처럼 편리하게 ClientRegistration을 설정할 수 있다
  - ClientRegistration clientRegistration = ClientRegistrations.fromIssuerLocation("https://idp.example.com/issuer").build();
  - 위 코드는 200 응답을 받을 때까지 https://idp.example.com/.well-known/openid-configuration,
  
    https://idp.example.com/.well-known/oauth-authorization-server에 차례대로 질의해본다

<img width="1015" alt="image" src="https://user-images.githubusercontent.com/40031858/200227691-5fec4f06-db3b-41d8-bbef-9b607f543dd7.png">

<img width="1430" alt="image" src="https://user-images.githubusercontent.com/40031858/200227721-dd35f479-aae1-438d-a1f5-16c78886185d.png">

<img width="1307" alt="image" src="https://user-images.githubusercontent.com/40031858/200227755-c9d1013a-bd6b-4544-a9dc-26f3ee373773.png">

<img width="1411" alt="image" src="https://user-images.githubusercontent.com/40031858/200227786-1a69ea08-9eaa-4f1c-874f-7cb0da1cf572.png">

### ClientRegistrationRepository 개념

- ClientRegistrationRepository 는 OAuth 2.0 & OpenID Connect 1.0 의 ClientRegistration 저장소 역할을 한다.
- 클라이언트 등록 정보는 궁극적으로 인가 서버가 저장하고 관리하는데 이 레포지토리는 인가 서버에 일차적으로 저장된 클라이언트 등록 정보의 일부를 검색하는 기능을 제공한다.
- 스프링 부트 2.X 자동 설정은 spring.security.oauth2.client.registration.[registrationId] 하위 프로퍼티를 ClientRegistration 인스턴스에 바인딩하며, 

  각 ClientRegistration 객체를 ClientRegistrationRepository 안에 구성한다.
- ClientRegistrationRepository 의 디폴트 구현체는 InMemoryClientRegistrationRepository 다.
- 자동 설정을 사용하면 ClientRegistrationRepository 도 ApplicationContext 내 @Bean 으로 등록하므로 필요하다면 원하는 곳에 의존성을 주입할 수 있다.

#### `의존성 주입 예시`

```java
@RestController
public class IndexPageController {   
  @Autowired 
  private ClientRegistrationRepository clientRegistrationRepository;  
  
  @GetMapping("/")   
  public String index() {
    ClientRegistration clientRegistration =
    this.clientRegistrationRepositoryfindByRegistrationId("keycloak");   
    return "index";   
  }   
}
```

### ClientRegistration / ClientRegistrationRepository 빈 등록하기

```java
public class Abcd {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistration.withRegistrationId("keycloak").
                                 clientId("keycloak-client-id").
                                 clientSecret("keycloak-client-secret").
            clientAuthenticationMethod(ClientAuthenticationMethod.BASIC).
            authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE).
            redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}").
            scope("openid", "profile", "email", "address", "phone")
            .authorizationUri("http://localhost:8080/realms/oauth2").
            tokenUri("http://localhost:8080/realms/oauth2/token")
            .userInfoUri("http://localhost:8080/realms/oauth2/userinfo").
            userNameAttributeName(IdTokenClaimNames.SUB)
            .jwkSetUri("http://localhost:8080/realms/oauth2/certs").
            clientName("Keycloak").build();
    }

}

```
