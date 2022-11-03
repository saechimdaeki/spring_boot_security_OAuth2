# OAuth 2.0 Grant Type 개요

## 권한 부여 유형

- 권한부여란 클라이언트가 사용자를 대신해서 사용자의 승인하에 인가서버로부터 권한을 부여받는 것을 의미한다
- OAuth 2.0 메커니즘은 아래와 같은 권한 부여 유형들을 지원하고 있으며 일부는 Deprecated 되었다

#### 1. Authorization Code Grant Type

- 권한 코드 부여 타입, 서버 사이드 애플리케이션(웹 애플리케이션), 보안에 가장 안전한 유형 

#### 2. Implicit Grant Type (Deprecated)
- 암시적 부여 타입, 공개 클라이언트 애플리케이션(SPA 기반 자바스크립트 앱, 모바일 앱), 보안에 취약

#### 3. Resource Owner Password Credentials Grant Type (Deprecated)

- 리소스 사용자 비밀번호 자격증명 부여 타입, 서버 어플리케이션, 보안에 취약

#### 4. Client Credentials Grant Type

- 클라이언트 자격 증명 권한 부여 타입 , UI or 화면이 없는 서버 어플리케이션

#### 5. Refresh Token Grant Type

- 새로고침 토큰 부여 타입, Authorization Code, Resource Owner Password Type 에서 지원


#### 6. PKCE-enhanced Authorization Code Grant Type

- PKCE 권한 코드 부여 타입 , 서버 사이드 어플리케이션, 공개 클라이언트 어플리케이션


<img width="1137" alt="image" src="https://user-images.githubusercontent.com/40031858/199228471-d80e61ea-1b54-44dc-8431-4f75630459ce.png">


## 매개 변수 용어

#### 1. client_id

- 인가서버에 등록된 클라이언트에 대해 생성된 고유 키

#### 2. client_secret

- 인가서버에 등록된 특정 클라이언트의 client_id 에 대해 생성된 비밀 값

#### 3. response_type

- 애플리케이션이 권한 부여 코드 흐름을 시작하고 있음을 인증 서버에 알려준다
- code, token, id_token 이 있으며 token, id_token 은 implicit 권한부여유형에서 지원해야 한다
- 서버가 쿼리 문자열에 인증 코드(code), 토큰(token, id_token) 등을 반환

#### 4. grant_type

- 권한 부여 타입 지정 - authorization_code, password, client_credentials, refresh_token


#### 5. redirect_uri

- 사용자가 응용 프로그램을 성공적으로 승인하면 권한 부여 서버가 사용자를 다시 응용 프로그램으로 리디렉션한다
- redirect_uri 가 초기 권한 부여 요청에 포함된 경우 서비스는 토큰 요청에서도 이를 요구해야 한다. 
- 토큰 요청의 redirect_uri 는 인증 코드를 생성할 때 사용된 redirect_uri 와 정확히 일치해야 한다. 그렇지 않으면 서비스는 요청을 거부해야 한다.

#### 6. scope

- 어플리케이션이 사용자 데이터에 접근하는 것을 제한하기 위해 사용된다 – email profile read write..
- 사용자에 의해 특정 스코프로 제한된 권한 인가권을 발행함으로써 데이터 접근을 제한한다

#### 7. state

- 응용 프로그램은 임의의 문자열을 생성하고 요청에 포함하고 사용자가 앱을 승인한 후 서버로부터 동일한 값이 반환되는지 확인해야 한다. 
- 이것은 CSRF 공격 을 방지하는 데 사용된다


---

## Authorization Code Grant Type - 권한 부여 코드 승인 방식

### 개요

#### 1. 흐름 및 특징
1) 사용자가 애플리케이션을 승인하면 인가서버는 Redirect URI로 임시 코드 담아서 애플리케이션으로 다시 리다이렉션한다
2) 애플리케이션은 해당 임시 코드를 인가 서버로 전달하고 액세스 토큰으로 교환한다
3) 애플리케이션이 액세스 토큰을 요청할 때 해당 요청을 클라이언트 암호로 인증할 수 있으므로 공격자가 인증 코드를 가로채서 스스로 사용할 위험이 줄어듬
4) 액세스 토큰이 사용자 또는 브라우저에 표시되지 않고 애플리케이션에 다시 전달하는 가장 안전한 방법이므로 토큰이 다른 사람에게 누출될 위험이 줄어듬

#### 2. 권한 부여 코드 요청시 매개변수
- response_type = code (필수)
- client_id (필수)
- redirect_uri (선택사항)
- scope (선택사항)
- state (선택사항)

#### 3. 액세스토큰 교환 요청 시 매개변수
- grant_type = authorization_code (필수)
- code (필수)
- redirect_uri (필수 : 리다이렉션 URL이 초기 승인 요청에 포함된 경우)
- client_id (필수)
- client_secret (필수)

<img width="1066" alt="image" src="https://user-images.githubusercontent.com/40031858/199395638-2c445fbb-35da-4e7f-83bb-bf1a3ef7d5ef.png">


<img width="1125" alt="image" src="https://user-images.githubusercontent.com/40031858/199395850-cb58d1e2-8224-43b2-8c73-a75ef8b3b420.png">

<img width="1078" alt="image" src="https://user-images.githubusercontent.com/40031858/199395907-58981caf-1444-49e8-8542-6fd7501d86c4.png">

<img width="1138" alt="image" src="https://user-images.githubusercontent.com/40031858/199396013-9f0f9889-cb14-454c-ada5-33a8890d7b1b.png">


---

## Implicit Grant Type - 암묵적 승인 방식

### 개요

#### 1. 흐름 및 특징

- 클라이언트에서 Javascript 및 HTML 소스 코드를 다운로드한 후 브라우저는 서비스에 직접 API 요청을 한다
- 코드 교환 단계를 건너뛰고 대신 액세스 토큰이 쿼리 문자열 조각으로 클라이언트에 즉시 반환된다
- 이 유형은 back channel이 없으므로 refresh token을 사용하지 못한다
- 토큰 만료 시 애플리케이션이 새로운 access token을 얻으려면 다시 OAuth 승인 과정을 거쳐야 한다

#### 2. 권한 부여 승인 요청 시 매개변수

- response_type = token (필수), id_token
- client_id (필수)
- redirect_uri (필수)
- scope (선택사항)
- state (선택사항)

---

## Resource Owner Password Credentials Grant Type - 패스워드 자격증명 승인 방식

### 개요

#### 1. 흐름 및 특징

- 애플리케이션이 사용자 이름과 암호를 액세스 토큰으로 교환할 때 사용된다
- 타사 애플리케이션이 이 권한을 사용하도록 허용해서는 안되고 고도의 신뢰할 자사 애플리케이션에서만 사용해야 한다

#### 2. 권한 부여 승인 요청 시 매개 변수

- grant_type = password (필수)
- username (필수)
- password (필수)
- client_id (필수)
- client_secret (필수)
- scope (선택사항)

<img width="1128" alt="image" src="https://user-images.githubusercontent.com/40031858/199689177-18d3deb3-11e8-488a-aec1-b9e135e22f16.png">


<img width="1091" alt="image" src="https://user-images.githubusercontent.com/40031858/199689242-718d7191-3987-4f14-af5f-1670850df456.png">