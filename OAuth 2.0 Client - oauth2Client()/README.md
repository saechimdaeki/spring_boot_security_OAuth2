# OAuth 2.0 Client - oauth2Client()

## OAuth2ClientConfigurer 초기화 이해

<img width="1411" alt="image" src="https://user-images.githubusercontent.com/40031858/202156064-80ae8009-aed8-41ea-994a-d34dc395d6b9.png">

<img width="1437" alt="image" src="https://user-images.githubusercontent.com/40031858/202156193-94549c0c-a62c-45f6-bf37-4f828a136a8d.png">


--- 

## OAuth2AuthorizedClient

### 개념

- OAuth2AuthorizedClient 는 인가받은 클라이언트를 의미하는 클래스다. 
- 최종 사용자(리소스 소유자)가 클라이언트에게 리소스에 접근할 수 있는 권한을 부여하면, 클라이언트를 인가된 클라이언트로 간주한다
- OAuth2AuthorizedClient 는 AccessToken 과 RefreshToken 을 ClientRegistration (클라이언트)와 권한을 부여한 최종 사용자인 Principal과 함께 묶어 준다
- OAuth2AuthorizedClient 의 AccessToken 을 사용해서 리소스 서버의 자원에 접근 할 수 있으며 인가서버와의 통신으로 토큰을 검증할 수 있다
- OAuth2AuthorizedClient 의 ClientRegistration 과 AccessToken 을 사용해서 UserInfo 엔드 포인트로 요청할 수 있다

<img width="474" alt="image" src="https://user-images.githubusercontent.com/40031858/202844782-e2d03319-fcd4-4a14-a580-819392401204.png">

- OAuth2AuthorizedClientRepository
  - OAuth2AuthorizedClientRepository 는 다른 웹 요청이 와도 동일한 OAuth2AuthorizedClient 를 유지하는 역할을 담당한다
  - OAuth2AuthorizedClientService 에게 OAuth2AuthorizedClient 의 저장, 조회, 삭제 처리를 위임한다

    <img width="358" alt="image" src="https://user-images.githubusercontent.com/40031858/202844825-f064ce3b-20ef-4897-bff9-66efc730b6ee.png">

- OAuth2AuthorizedClientService
  - OAuth2AuthorizedClientService 는 어플리케이션 레벨에서 OAuth2AuthorizedClient 를 관리(저장, 조회, 삭제 )하는 일이다.

    <img width="358" alt="image" src="https://user-images.githubusercontent.com/40031858/202844858-e5e90b68-fdf9-44bb-998e-6ed1378c6353.png">


- 웹 어플리케이션에서 활용
  - OAuth2AuthorizedClientRepository 나 OAuth2AuthorizedClientService 는 OAuth2AuthorizedClient 에서 OAuth2AccessToken 을 찾을 수 있는 기능을 제공하므로 보호중인 리소스 요청을 시작할 때 사용할 수 있다


    <img width="971" alt="image" src="https://user-images.githubusercontent.com/40031858/202844883-071c3f5b-db4b-4405-9931-b2f8ce40e0d4.png">


### OAuth2AuthorizationCodeGrantFilter 개념

- Authorization Code Grant 방식으로 권한 부여 요청을 지원하는 필터
- 인가서버로부터 리다이렉트 되면서 전달된 code 를 인가서버의 Access Token 으로 교환한다.
- OAuth2AuthorizedClientRepository 를 사용하여 OAuth2AuthorizedClient 를 저장 후 클라이언트의 Redirect Uri 로 이동한다

    <img width="559" alt="image" src="https://user-images.githubusercontent.com/40031858/202844930-a5443e4d-1e88-4763-bbaa-5c54c7ed31bc.png">

- 실행 조건
  - 요청 파라미터에 code 와 state 값이 존재하는지 확인
  - OAuth2AuthorizationRequest 객체가 존재하는지 확인



    













