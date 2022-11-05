# OAuth 2.0 Open ID Connect

## 개요 및  특징

- OpenID Connect 1.0은 OAuth 2.0 프로토콜 위에 구축된 ID 계층으로 OAuth 2.0을 확장하여 인증 방식을 표준화 한 OAuth 2.0 기반의 인증 프로토콜이다
- scope 지정 시 "openid"를 포함하면 OpenID Connect 사용이 가능하며 인증에 대한 정보는 ID 토큰(ID Token)이라고 하는 JSON 웹 토큰(JWT)으로 반환된다
- OpenID Connect는 클라이언트가 사용자 ID를 확인할 수 있게 하는 보안 토큰인 ID Token을 제공한다

<img width="1070" alt="image" src="https://user-images.githubusercontent.com/40031858/200118241-383a7016-a53a-4048-9c52-477e221b5bd2.png">

### OpenID Connect Discovery 1.0 Provider Metadata

- OpenID Connect를 사용하기 위해 필요한 모든 엔드 포인트 및 공개 키 위치 정보를 포함하여 OpenID 공급자의 구성에 대한 클레임 집합을 나타낸다
- 검색 문서 경로 /.well-known/openid-configuration
  - https://[base-server-url]/.well-known/openid-configuration 에서  검색 할 수 있다

<img width="697" alt="image" src="https://user-images.githubusercontent.com/40031858/200118385-f8793d3d-bc0b-49ed-8542-93a5a4353fee.png">


---

## ID Token & Scope

### ID Token

- ID 토큰은 사용자가 인증 되었음을 증명하는 결과물로서 OIDC 요청 시 access token 과 함께 클라이언트에게 전달되는 토큰이다
- ID 토큰은 JWT(JSON 웹 토큰)으로 표현되며 헤더, 페이로드 및 서명으로 구성된다
- ID 토큰은 개인 키로 발급자가 서명하는 것으로서 토큰의 출처를 보장하고 변조되지 않았음을 보장한다
- 애플리케이션은 공개 키로 ID 토큰을 검증 및 유효성을 검사하고 만료여부 등 토큰의 클레임을 확인한다
- 클라이언트는 클레임 정보에 포함되어 있는 사용자명, 이메일을 활용하여 인증 관리를 할 수 있다

### ID Token vs Access Token - 예제로 확인

- ID Token 은 API 요청에 사용해서는 안되며 사용자의 신원확인을 위해 사용되어져야 한다
- Access Token 은 인증을 위해 사용해서는 안되며 리소스에 접근하기 위해 사용되어져야 한다

<img width="1285" alt="image" src="https://user-images.githubusercontent.com/40031858/200118505-1c1cc88e-76bf-4157-bbd1-d35bdd0d5cb9.png">

<img width="1374" alt="image" src="https://user-images.githubusercontent.com/40031858/200118585-d9f89b9e-8951-491e-878e-3123452f5b10.png">

### OIDC 로그인 요청

#### OIDC 상호 적용 행위자

1. OpenID Provider

- 줄여서 OP 라고 하며 OpenID 제공자로서 최종 사용자를 인증하고 인증 결과와 사용자에 대한 정보를 신뢰 당사자에게 제공할 수 있는 Oauth 2.0 서버를 의미한다

2. Relying Party

- 줄여서 RP 라고 하며 신뢰 당사자로서  인증 요청을 처리하기 위해 OP에 ＂의존＂하는 Oauth 2.0 애플리케이을 의미한다

#### 흐름

1. RP는 OP에 권한 부여 요청을 보낸다.
2. OP는 최종 사용자를 인증하고 권한을 얻는다.
3. OP는 ID 토큰과 액세스 토큰으로 응답한다.
4. RP는 Access Token을 사용하여 UserInfo 엔드포인트에 요청을 보낼 수 있다.
5. UserInfo 엔드포인트는 최종 사용자에 대한 클레임을 반환한다.


<img width="1414" alt="image" src="https://user-images.githubusercontent.com/40031858/200118666-ad6cd571-5ca3-4ac4-a2a8-a64f68dfe260.png">





