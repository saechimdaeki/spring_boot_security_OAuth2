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