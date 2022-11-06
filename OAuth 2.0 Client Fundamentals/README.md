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